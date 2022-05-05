#!/bin/bash

#Build the database container image
cd psql
docker build -t trading-psql .
cd ..

#build the app container image
docker build -t trading-app .