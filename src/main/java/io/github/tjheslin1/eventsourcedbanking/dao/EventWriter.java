package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.settings.Settings;
import org.bson.Document;

import static io.github.tjheslin1.eventsourcedbanking.dao.MongoOperations.collectionCreateIfNotExistsForDatabase;

public class EventWriter {

    private final MongoClient mongoClient;
    private Settings settings;

    public EventWriter(MongoConnection mongoConnection, Settings settings) {
        this.mongoClient = mongoConnection.connection();
        this.settings = settings;
    }

    public void write(BalanceEvent balanceEvent, JsonRenderer jsonRenderer) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(settings.mongoDbName());

        String collectionName = balanceEvent.getClass().getSimpleName();
        MongoCollection<Document> balanceEventCollection = collectionCreateIfNotExistsForDatabase(collectionName, eventStoreDb);

        BasicDBObject docToWrite = jsonRenderer.renderBalanceEvent(balanceEvent);


    }
}
