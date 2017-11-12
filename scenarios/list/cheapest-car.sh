#!/usr/bin/env bash

echo "Getting cheapest car"

result=$(curl -X POST \
  http://flow:8181/tars/cars/search \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
    "city": "France",
    "dateFrom": "2017-12-20",
    "dateTo": "2017-12-25"
}' -s)

if [[ $(echo ${result} | grep "price") ]]
then
    price=$(echo ${result} | jq .price)
    echo Found cheapest price : ${price}
else
    echo No car found
fi
