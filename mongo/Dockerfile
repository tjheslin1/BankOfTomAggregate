FROM mongo

RUN mkdir -p /data/db
ADD restaurant.json /data/db

RUN umongod --fork --logpath=/tmp/mongodb.log && sleep 20 && \
mongoimport  --db test --collection restaurants --type json --file /data/db/restaurant.json  #&& mongod --shutdown

EXPOSE 27017

ENTRYPOINT ["/usr/bin/mongod"]

