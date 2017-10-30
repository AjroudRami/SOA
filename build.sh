#!/bin/sh
cd services
echo Building services

# Declaring our dependencies
dependencies=(business-travels cars flights hotels travel-report)

for dependency in "${dependencies[@]}"
do
  echo Building $dependency
  cd $dependency
  ./build.sh
  cd ..
done

cd ..
echo Kindly building integration
cd integration
./build.sh
cd ..
