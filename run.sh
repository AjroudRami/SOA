#!/bin/sh

# Waiting for the services to be alive
echo Waiting for the services to be alive
sleep 10

# We will do 48x 5 seconds loop = four minutes
cd integration
for i in $(seq 1 48)
do
    # Save start time
    START=$(date +%s)

    docker-compose run --no-deps client

    # Compute execution time
    END=$(date +%s)
    DIFF=$(( $END - $START ))

    # Sleep if we need to sleep
    if [ $DIFF -ge 0 ]
    then
        sleep $((5 - DIFF))
    fi
done
cd ..