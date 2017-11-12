#!/usr/bin/env bash

echo "Getting cheapest hotel"

result=$(curl -X POST \
  http://flow:8181/tars/hotels/search \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
    "destination": "Liudu",
    "timestamp": 10923443534
}' -s)

price=$(echo ${result} | jq .price)
echo Found cheapest price : ${price}