package io.github.tjheslin1.esb.infrastructure.application.cqrs.command;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.esb.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.EventWiring;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;
import org.bson.Document;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionCreateIfNotExistsForDatabase;

public class MongoBalanceCommandWriter implements BalanceCommandWriter {

    private final MongoClient mongoClient;
    private MongoSettings mongoSettings;

    public MongoBalanceCommandWriter(MongoClient mongoClient, MongoSettings mongoSettings) {
        this.mongoClient = mongoClient;
        this.mongoSettings = mongoSettings;
    }

    @Override
    public void write(BalanceCommand balanceCommand, EventWiring eventWiring) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(mongoSettings.mongoDbName());

        MongoCollection<Document> collection = collectionCreateIfNotExistsForDatabase(eventWiring.collectionName(), eventStoreDb);
        Document balanceEventDoc = eventWiring.marshaller().marshallBalanceEvent(balanceCommand);

        try {
            collection.insertOne(balanceEventDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
