#!/bin/bash

#store and validate arguments
if [ $# -ne 5 ]; then
  echo "Usage: ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password"
  exit 1
fi

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [[ ! "$psql_port" =~ ^[0-9]+$ || $psql_port -ge 65535 || $psql_port -le 0 ]]; then
  echo "Port number must be a valid natural number no higher than 65535"
  exit 1
fi

#save hostname to a variable and check if hostname's data as already been entered
hostname=$(hostname -f)
export PGPASSWORD=$psql_password
host_id_query="SELECT id FROM host_info WHERE hostname = '$hostname';"
host_id="$(psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$host_id_query" | grep row \
    | sed 's/[^0-9]//g')"

#0 will be the value when there as been no such hostname created yet (extracted from the "0 rows" part of the result)
if [ $host_id -eq 0 ]; then

  #save result of lscpu command
  lscpu_out=`lscpu`

  #save full model name
  fullModelName="$(echo "$lscpu_out" | grep "Model name")"

  #hardware
  cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
  cpu_architecture="$(echo "$lscpu_out" | grep Architecture | cut - -f 2 -d ':' | xargs)"
  cpu_model="$(echo "$fullModelName" | cut - -f 2 -d ':' | cut - -f 1 -d '@' | xargs)"
  cpu_mhz="$(echo "$lscpu_out" | grep "CPU MHz" | cut - -f 2 -d ':' | xargs)"
  l2_cache="$(echo "$lscpu_out" | grep "L2 cache" | cut - -f 2 -d ':' | xargs | sed 's/[^0-9]//g')"
  total_mem="$(cat /proc/meminfo | grep MemTotal | cut - -f 2 -d ':' | xargs | sed 's/[^0-9]//g')"

  timestamp="$(date +"%Y-%m-%d %H:%M:%S")"

  #Inserting server usage data into host_usage table
  insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, \
      timestamp) VALUES('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', $cpu_mhz, '$l2_cache', \
      '$total_mem','$timestamp');"

  psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

  exit $?
fi

exit 0