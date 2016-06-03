#!/usr/bin/env bash

# TODO find a way to not need sudo - write to a new temp dir every run using LocalDateTime?
sudo rm -rf /tmp/mongo
mkdir /tmp/mongo
cp restaurant.json /tmp/mongo
docker run --name mongo_banking -d -p 27017:27017 -v /tmp/mongo:/data/db mongo-banking

exit $?
