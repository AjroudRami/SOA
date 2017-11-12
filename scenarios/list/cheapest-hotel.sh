#!/usr/bin/env bash

echo "Getting cheapest hotel"

result=$(curl -X POST \
  http://flow:8181/tars/hotels/search \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
    "destination": "Lyon",
    "timestamp": 710170930
}' -s)



if [[ $(echo ${result} | grep "roomCost") ]]
then
   price=$(echo ${result} | jq .roomCost)
    echo Found cheapest price : ${price}
else
    echo No hotel found
fi