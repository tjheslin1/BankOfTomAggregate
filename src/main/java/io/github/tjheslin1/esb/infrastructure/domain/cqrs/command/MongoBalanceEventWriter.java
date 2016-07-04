package io.github.tjheslin1.esb.infrastructure.domain.cqrs.command;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.domain.events.EventWiring;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;
import org.bson.Document;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionCreateIfNotExistsForDatabase;

public class MongoBalanceEventWriter implements io.github.tjheslin1.esb.application.cqrs.command.BalanceEventWriter {

    private final MongoClient mongoClient;
    private MongoSettings mongoSettings;

    public MongoBalanceEventWriter(MongoClient mongoClient, MongoSettings mongoSettings) {
        this.mongoClient = mongoClient;
        this.mongoSettings = mongoSettings;
    }

    @Override
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
