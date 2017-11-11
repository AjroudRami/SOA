#!/usr/bin/env bash

echo ------ Building external services ------

echo Building Team 3 Flight service
cd team-3/services/rpc/
mvn clean package
cd ../../..

pwd

echo Building Team 5 services
cd team-5/services/hotelRPC/
mvn clean package
cd ../../..

echo Building Team 8 services
cd team-8/services/car/
mvn clean package
cd ../../../..

echo ----- End of external services building -----