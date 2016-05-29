#!/usr/bin/env bash

#docker run --name mongo_banking -d -p 27017:27017 -v /data/mongo-docker:/data/db mongo-banking
docker run --name mongo_banking -d -p 27017:27017 mongo-banking
#docker run --name mongo_banking -d -p 27017:27017 mongo-banking-${project.version}

exit $?