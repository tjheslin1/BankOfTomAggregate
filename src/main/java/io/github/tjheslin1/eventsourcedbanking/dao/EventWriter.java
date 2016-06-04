package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.settings.Settings;

import static io.github.tjheslin1.eventsourcedbanking.dao.MongoOperations.collectionCreateIfNotExistsForDatabase;

public class EventWriter {

    private final MongoClient mongoClient;
    private Settings settings;

    public EventWriter(MongoClient mongoClient, Settings settings) {
        this.mongoClient = mongoClient;
        this.settings = settings;
    }


    // TODO get the database here or pass in?
    public void write(BalanceEvent balanceEvent, JsonRenderer jsonRenderer) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(settings.mongoDbName());

        collectionCreateIfNotExistsForDatabase(balanceEvent.collectionName(), eventStoreDb);

        BasicDBObject docToWrite = jsonRenderer.renderBalanceEvent(balanceEvent);


    }
}
