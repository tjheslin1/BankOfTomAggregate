#!/usr/bin/env bash

docker run -d -p 8085:8085 --link mongo_banking:events_store --name bank_of_tom_aggregate bank-of-tom-aggregate

exit $?
