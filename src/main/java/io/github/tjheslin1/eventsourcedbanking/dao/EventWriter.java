package io.github.tjheslin1.eventsourcedbanking.dao;

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

    public EventWriter(MongoClient mongoClient, Settings settings) {
        this.mongoClient = mongoClient;
        this.settings = settings;
    }

    // TODO get the database here or pass in?
    public void write(BalanceEvent balanceEvent, JsonMarshaller jsonMarshaller) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(settings.mongoDbName());

        MongoCollection<Document> collection = collectionCreateIfNotExistsForDatabase(balanceEvent.collectionName(), eventStoreDb);
        Document balanceEventDoc = jsonMarshaller.renderBalanceEvent(balanceEvent);

        collection.insertOne(balanceEventDoc);
    }
}
