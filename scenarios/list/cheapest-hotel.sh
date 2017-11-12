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

price=$(echo ${result} | jq .roomCost)
echo Found cheapest price : ${price}