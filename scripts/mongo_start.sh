#!/usr/bin/env bash

rm -rf /tmp/mongo
mkdir /tmp/mongo
cp restaurant.json /tmp/mongo
docker run --name mongo_banking -d -p 27017:27017 -v /tmp/mongo:/data/db mongo-banking

exit $?
