#!/bin/sh

echo Press ^C to stop the script
cd integration

# Our different services
services=(internal-flights internal-cars internal-hotels business-travels travel-report external-cars external-flights external-hotels)
length=${#services[@]}

# We kill a random service every 10 seconds (and start it again 10 seconds later)
while [ 1=1 ]
do
    # Get a random service
    random=$((RANDOM % length))
    service=${services[$random]}

    # Kill it
    echo KILLING ${service}
    docker-compose stop ${service}

    # Wait for 10 seconds
    sleep 10

    # Start it again
    echo STARTING ${service}
    docker-compose start ${service}
done

cd ..