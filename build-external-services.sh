#!/bin/bash

echo ------ Building external services ------

cd external-services/

echo Building Team 3 services
cd team-3
bash build.sh
cd ..

echo Building Team 5 services
cd team-5
bash build.sh
cd ..

echo Building Team 8 services
cd team-8
bash build.sh
cd ..

cd ..

echo ----- End of external services building -----