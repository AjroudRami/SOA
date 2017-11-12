#!/usr/bin/env bash

echo "Getting cheapest flight"

result=$(curl -X POST \
  http://flow:8181/tars/flights/search \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
    "to": "New-York",
    "from": "Paris",
    "departure": 710170930
}' -s)

price=$(echo ${result} | jq .price)
echo Found cheapest price : ${price}
