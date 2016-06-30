#!/usr/bin/env bash

docker build --tag=event-sourced-banking ../target/docker-ready

exit $?