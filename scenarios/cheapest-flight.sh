#!/usr/bin/env bash

echo "Getting cheapest flight"

curl -X POST \
  http://localhost:8181/tars/flights/search \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 1dacc6ea-68c7-f2bc-45a0-1f7e32c9c89e' \
  -d '{
    "to": "New-York",
    "from": "Paris",
    "departure": 710170930
}'