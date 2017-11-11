#!/bin/sh

# Waiting for the services to be alive
echo Waiting for the services to be alive
sleep 10

# We will do 24x 5 seconds loop = two minutes
for i in $(seq 1 24)
do
    # Save start time
    START=$(date +%s)
    for file in scenarios/*
    do
        sh ${file}
    done
    # Compute execution time
    END=$(date +%s)
    DIFF=$(( $END - $START ))

    # Sleep if we need to sleep
    if [ $DIFF -ge 0 ]
    then
        sleep $((5 - DIFF))
    fi
done