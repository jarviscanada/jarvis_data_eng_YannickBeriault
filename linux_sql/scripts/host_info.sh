#!/bin/bash

#store and validate arguments
if [ $# -ne 5 ]
  echo "Usage: ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password"
  exit 1
fi

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [[ "$psql_port" =~ ^[0-9]+$ || "$psql_port" -gt 65535 || "$psql_port" -lt 0 ]] then
  echo "Port number must be a valid natural number no higher than 65535"
  exit 1
fi

#save hostname to a variable and check if hostname's data as already been entered
hostname=$(hostname -f)
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

if [ ! -z "$hostname" ] then

  #save number of CPU to a variable
  lscpu_out=`lscpu`
  cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)

  #save full model name
  fullModelName="$(echo "$lscpu_out" | grep "Model name")"

  #save memory data
  memoryData="$(cat /proc/meminfo)"

  #hardware
  hostname="$(hostname -f)"
  cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
  cpu_architecture="$(echo "$lscpu_out" | grep Architecture | cut - -f 2 -d ':' | xargs)"
  cpu_model="$(echo "$fullModelName" | cut - -f 2 -d ':' | cut - -f 1 -d '@' | xargs)"
  cpu_mhz="$(echo "$fullModelName" | cut - -f 2 -d ':' | cut - -f 2 -d '@' | xargs)"
  l2_cache="$(echo "$lscpu_out" | grep "L2 cache" | cut - -f 2 -d ':' | xargs)"
  total_mem="$(cat $memoryData | grep MemTotal | cut - -f 2 -d ':' | xargs)"
  timestamp="$(date +"%Y-%m-%d %H:%M:%S")"

  #Inserting server usage data into host_usage table
  insert_stmt="INSERT INTO host_usage(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, \
      timestamp) VALUES('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$L2_cache', \
      '$total_mem','$timestamp');"

  #set up env var for pql cmd
  export PGPASSWORD=$psql_password
  #Insert date into a database
  psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

  exit $?
fi

exit 0