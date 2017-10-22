#!/usr/bin/env bash
mvn clean package
docker build -t soa-team-1/travel-report:latest .
