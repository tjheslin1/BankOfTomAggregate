#!/usr/bin/env bash

docker rm -fv mongo_banking
docker rmi mongo-banking

exit $?