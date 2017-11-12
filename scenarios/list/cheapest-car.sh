#!/usr/bin/env bash

echo "Getting cheapest car"

result=$(curl -X POST \
  http://flow:8181/tars/cars/search \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
    "city": "New-York",
    "dateFrom": "2018-10-2",
    "dateTo": "2018-10-2"
}' -s)

price=$(echo ${result} | jq .price)
echo Found cheapest price : ${price}