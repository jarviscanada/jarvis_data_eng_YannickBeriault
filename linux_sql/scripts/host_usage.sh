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

#extract host id from hostname
hostname=$(hostname -f)
export PGPASSWORD=$psql_password
host_id_query="SELECT id FROM host_info WHERE hostname = '$hostname';"
host_id="$(psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$host_id_query" | grep row \
    | sed 's/[^0-9]//g')"

#save CPU(s) usage stats for further calculations
allCpu="$(cat /proc/stat | head -n 1 | cut -f 2- -d ' ')"
totalCpuTime="$(echo $allCpu | sed 's/ /\n/g' | paste -sd+ - | bc)"
idleTime="$(echo $allCpu | cut -f 4 -d ' ')"
kernelTime="$(echo $allCpu | cut -f 3 -d ' ')"

#variables for i/o blocks stats
vmStatNmbrs="$(vmstat --unit M | tail -n 1)"
vmBi="$(echo $vmStatNmbrs | cut -f 9 -d ' ')"
vmBo="$(echo $vmStatNmbrs | cut -f 10 -d ' ')"

#usage
memory_free="$(cat /proc/meminfo | grep MemFree | cut - -f 2 -d ':' | xargs | sed 's/[^0-9]//g')"
cpu_idle=$((($idleTime * 100) / $totalCpuTime))
cpu_kernel=$((($kernelTime * 100) / $totalCpuTime))
disk_io=$(($vmBi + $vmBo))
disk_available="$(cat /proc/meminfo | grep MemAvailable | cut - -f 2 -d ':' | xargs | sed 's/[^0-9]//g')"

timestamp="$(date +"%Y-%m-%d %H:%M:%S")"

#Inserting server usage data into host_usage table
insert_stmt="INSERT INTO host_usage(host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available, timestamp) \
    VALUES($host_id, $memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available, '$timestamp');"

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

exit $?