#!/usr/bin/env bash

docker run --name event_sourced_banking -d event-sourced-banking

exit $?
