#!/bin/bash

docker run --name trading-psql-prod -e POSTGRES_PASSWORD=password --network trading-net trading-psql


docker run --name trading-app-prod -e PSQL_DB=jrvstrading -e PSQL_USER=postgres -e PSQL_PASSWORD=password \
  -e IEX_PUB_TOKEN=${IEX_PUB_TOKEN} -e PSQL_HOST=172.17.0.1 -e PSQL_PORT=8085 --network trading-net \
  -p 8080:8080 -t trading-app