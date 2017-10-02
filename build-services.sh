#!/bin/sh
cd services
echo Building services

# Declaring our dependencies
dependencies=(business-travels cars flights hotels)

for dependency in "${dependencies[@]}"
do
  echo Building $dependency
  cd $dependency
  ./build.sh
  cd ..
done
