package io.github.tjheslin1.eventsourcedbanking.dao.reading;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.settings.Settings;

public class BalanceEventReader {

    private final MongoClient mongoClient;
    private Settings settings;

    public BalanceEventReader(MongoClient mongoClient, Settings settings) {
        this.mongoClient = mongoClient;
        this.settings = settings;
    }

    // TODO get the database here or pass in?
    public BalanceEvent read(BalanceEvent balanceEvent, BalanceEventJsonUnmarshaller jsonUnmarshaller) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(settings.mongoDbName());

//        eventStoreDb.
        return null;
    }
}
