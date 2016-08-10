#!/usr/bin/env bash

docker run -d \
    --name mongo_banking \
    -p 27017:27017 \
    -v /tmp/mongo:/data/db \
    mongo-banking

exit $?
