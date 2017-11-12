#!/usr/bin/env bash

echo "Getting cheapest car"

result=$(curl -X POST \
  http://flow:8181/tars/flights/search \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
    "to": "Miskolc",
    "from": "Lebowakgomo",
    "departure": 126
}' -s)

price=$(echo ${result} | jq .price)
echo Found cheapest price : ${price}