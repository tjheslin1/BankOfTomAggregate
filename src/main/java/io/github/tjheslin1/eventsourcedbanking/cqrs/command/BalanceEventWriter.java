package io.github.tjheslin1.eventsourcedbanking.cqrs.command;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.settings.Settings;
import org.bson.Document;

import static io.github.tjheslin1.eventsourcedbanking.cqrs.MongoOperations.collectionCreateIfNotExistsForDatabase;
import static io.github.tjheslin1.eventsourcedbanking.cqrs.MongoOperations.collectionNameForEvent;

public class BalanceEventWriter {

    private final MongoClient mongoClient;
    private Settings settings;

    public BalanceEventWriter(MongoClient mongoClient, Settings settings) {
        this.mongoClient = mongoClient;
        this.settings = settings;
    }

    // TODO get the database here or pass in?
    public void write(BalanceEvent balanceEvent, BalanceEventJsonMarshaller jsonMarshaller) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(settings.mongoDbName());

        MongoCollection<Document> collection = collectionCreateIfNotExistsForDatabase(
                collectionNameForEvent(balanceEvent.getClass()), eventStoreDb);

        Document balanceEventDoc = jsonMarshaller.marshallBalanceEvent(balanceEvent);

        collection.insertOne(balanceEventDoc);
    }
}
