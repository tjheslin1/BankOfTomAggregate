#!/usr/bin/env bash

docker pull mongo:3.2.6
docker run -p 27017:27017 --name mongo_event_sourced_banking -d mongo