#!/usr/bin/env bash

echo "**************************"
echo "Business travel simulation"
echo "**************************"


echo "Creation of a business travel report"

curl -X POST \
  http://localhost:8181/tars/business-travel/create/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 867f1e73-3f65-db1b-cb8c-4093cbbfa28b' \
  -d '{
"tickets": [
		{
			"ticketNumber": "123",
			"departureAirport": "NCE",
			"departureTimestamp": 123,
			"arrivalAirport": "AMS",
			"arrivalTimestamp": 1234
		}
	],
	"nights": [
		{
			"hotelId": "AMS-123",
			"nights": [
				{
					"departureTimestamp": 13456,
					"room": "BIG"
				}
			]
		}
	]
}'

echo "list all business travel report"

curl -X POST \
  http://localhost:8181/tars/business-travel/list/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: a7a3ae9c-ca6d-3ee7-5499-bf799a20a6be' \
  -d '{}'

echo "Approve a business travel report"

curl -X POST \
  http://localhost:8181/tars/business-travel/approve/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 722de09d-5f55-f516-ff69-a778a6015174' \
  -d '{"id": "b8166304-98f6-4c0e-bfcf-fd5c3739c6f2"}'