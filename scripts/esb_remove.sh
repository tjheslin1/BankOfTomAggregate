#!/usr/bin/env bash

docker rm -fv event_sourced_banking
docker rmi event-sourced-banking

exit $?