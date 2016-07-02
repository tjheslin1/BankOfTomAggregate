#!/usr/bin/env bash

cd ../target/docker-ready
docker build --tag=event-sourced-banking .

exit $?