#! /bin/sh

cmd=$1
db_username=$2
db_password=$3

sudo systemctl status docker || sudo systemctl start docker

#Here, we get to know if the container we are planning on using or creating already exists.
docker container inspect lil-postgres
container_status=$?

case $cmd in
  create)

    if [ $container_status -eq 0 ]; then
		  echo 'Container already exists'
		  exit 0
	  fi

    #Now we can create a place to store data and the actual instance, in two commands
  	docker volume create pgdata
  	docker run --rm --name lil-postgres -e POSTGRES_PASSWORD=password -d \
  	    -v $HOME/srv/postgres:/var/lib/postgresql/data -p 5432:5432 postgres
	  exit $?
	;;

  start|stop)

    if [ $container_status -ne 0 ]; then
      echo "Container has not been created."
      exit 1
    fi

    #Starting or stopping, depending on the "cmd" argument
	  docker container $cmd lil-postgres
	  exit $?
	;;

  *)

	  echo 'Illegal command'
	  echo 'Commands: start|stop|create'
	  exit 1
	;;
esac