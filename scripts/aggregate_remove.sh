#!/usr/bin/env bash

docker rm -fv bank_of_tom_aggregate
docker rmi bank-of-tom-aggregate

exit $?