#!/usr/bin/env bash

rm -rf /tmp/mongo
mkdir /tmp/mongo
cp restaurant.json /tmp/mongo
docker run --name mongo_banking -d -p 27017:27017 -v /tmp/mongo:/data/db mongo-banking

port=$(docker inspect mongo_banking | python -c 'import json,sys;obj=json.load(sys.stdin);print obj[0]["NetworkSettings"]["IPAddress"]')
docker exec -it mongo_banking sh -c "mongoimport -h ${port} --db test --collection restaurants --file /data/db/restaurant.json"

exit $?
