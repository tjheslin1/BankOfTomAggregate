#!/usr/bin/env bash

docker run --name mongo_banking -d -p 27017:27017 -v /tmp/mongo:/data/db mongo-banking

exit $?
