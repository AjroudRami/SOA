#!/usr/bin/env bash

echo "Creating a business travel"

result=$(curl -X POST \
  http://flow:8181/tars/business-travel/create/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
   "tickets":[
      {
         "ticketNumber":"123",
         "departureAirport":"NCE",
         "departureTimestamp":123,
         "arrivalAirport":"AMS",
         "arrivalTimestamp":1234
      }
   ],
   "nights":[
      {
         "hotelId":"AMS-123",
         "nights":[
            {
               "departureTimestamp":13456,
               "room":"BIG"
            }
         ]
      }
   ]
}' -s)

submissionId=$(echo ${result} | jq .submissionId)
echo Created a new business travel : ${submissionId}

result=$(curl -X POST \
  http://flow:8181/tars/business-travel/approve/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"id": '${submissionId}'}' -s)

trId=$(echo ${result} | jq .id)
echo Approved the business travel created the travel report of id : ${trId}

echo We will now end the travel report and have our money refund
result=$(curl -X POST \
  http://flow:8181/tars/travel-report/end/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 5b98265b-a072-820a-c8f4-08667d975cb8' \
  -d '{"id": '${trId}'}' -s)

status=$(echo ${result} | jq .status)
echo Our business travel now has the status : ${status}

