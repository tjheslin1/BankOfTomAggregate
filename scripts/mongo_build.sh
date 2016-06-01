#!/usr/bin/env bash

docker build --tag=mongo-banking ../mongo
#docker build --tag=mongo-banking-${project.version} ../mongo
#docker build --tag=mongo-banking-${version} ../mongo

exit $?