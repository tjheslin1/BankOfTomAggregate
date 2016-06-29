package io.github.tjheslin1.eventsourcedbanking.cqrs.command;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.EventWiring;
import io.github.tjheslin1.settings.MongoSettings;
import org.bson.Document;

import static io.github.tjheslin1.eventsourcedbanking.cqrs.MongoOperations.collectionCreateIfNotExistsForDatabase;

public class BalanceEventWriter {

    private final MongoClient mongoClient;
    private MongoSettings mongoSettings;

    public BalanceEventWriter(MongoClient mongoClient, MongoSettings mongoSettings) {
        this.mongoClient = mongoClient;
        this.mongoSettings = mongoSettings;
    }

    public void write(BalanceEvent balanceEvent, EventWiring eventWiring) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(mongoSettings.mongoDbName());

        MongoCollection<Document> collection = collectionCreateIfNotExistsForDatabase(eventWiring.collectionName(), eventStoreDb);
        Document balanceEventDoc = eventWiring.marshaller().marshallBalanceEvent(balanceEvent);

        try {
            collection.insertOne(balanceEventDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
