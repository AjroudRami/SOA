#!/bin/sh
mvn clean package
docker build -t soa-team-1/cars:latest .
