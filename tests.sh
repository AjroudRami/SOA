#!/bin/sh
echo Running tests. Assuming that the system is online and accessible on ports 8000-8003

cd tests

cd acceptation
echo Running acceptation tests
mvn clean generate-sources package verify
cd ..

cd stress
echo Running stress tests
mvn clean package gatling:test
cd ..
