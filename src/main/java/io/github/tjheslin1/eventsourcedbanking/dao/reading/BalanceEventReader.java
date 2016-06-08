package io.github.tjheslin1.eventsourcedbanking.dao.reading;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.settings.Settings;
import org.bson.Document;

import java.time.LocalDateTime;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class BalanceEventReader {

    private final MongoClient mongoClient;
    private Settings settings;

    public BalanceEventReader(MongoClient mongoClient, Settings settings) {
        this.mongoClient = mongoClient;
        this.settings = settings;
    }

    // TODO get the database here or pass in?
    public BalanceEvent read(int accountId, LocalDateTime timeOfEvent, BalanceEventJsonUnmarshaller jsonUnmarshaller, String collectionName) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(settings.mongoDbName());
        Document eventDoc = eventStoreDb.getCollection(collectionName)
                .find(and(
                        eq("accountId", accountId),
                        eq("timeOfEvent", timeOfEvent.toString())))
                .first();

        return jsonUnmarshaller.unmarshallBalanceEvent(eventDoc);
    }
}
