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

echo We will now add some expenses to our travel report

for i in $(seq 1 5)
do
    toAdd=$(( ( RANDOM % 5000 )  + 5000 ))
    result=$(curl -X POST \
      http://flow:8181/tars/travel-report/expenses/ \
      -H 'cache-control: no-cache' \
      -H 'content-type: application/json' \
      -H 'postman-token: 5b98265b-a072-820a-c8f4-08667d975cb8' \
      -d '{"id": '${trId}', "expenses": [{"amount": '${toAdd}', "description": "super expense"}]}' -s)
    amount=$(echo ${result} | jq .totalAmount)
    echo We added an expense, the total amount of our travel report is now : ${amount}
done


echo We will now end the travel report and maybe have our money refund
result=$(curl -X POST \
  http://flow:8181/tars/travel-report/end/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 5b98265b-a072-820a-c8f4-08667d975cb8' \
  -d '{"id": '${trId}'}' -s)

status=$(echo ${result} | jq .status)
echo Our business travel now has the status : ${status}

echo We will now add some explaination to our travel report
result=$(curl -X POST \
  http://flow:8181/tars/travel-report/explain/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 08af9196-3167-d643-8213-89526fc2136c' \
  -d '{"id": '${trId}', "explaination":"There was a strike, I had to book another flight back home"}')

status=$(echo ${result} | jq .explaination)

echo We will now validate the travel report to get the money refund
result=$(curl -X POST \
  http://flow:8181/tars/travel-report/approve/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 5b98265b-a072-820a-c8f4-08667d975cb8' \
  -d '{"id": '${trId}'}' -s)

status=$(echo ${result} | jq .status)
echo Our business travel now has the status : ${status}
