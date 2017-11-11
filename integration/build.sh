#!/usr/bin/env bash
echo kindly building soa-team-1/esb-bus
cd esb
bash build.sh
cd ..

echo kindly building soa-team-1/esb-bus
mvn clean
mvn package -DskipTests
docker build -t soa-team-1/esb-bus-flow .

docker-compose build
