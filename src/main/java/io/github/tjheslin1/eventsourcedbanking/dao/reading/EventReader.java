package io.github.tjheslin1.eventsourcedbanking.dao.reading;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.eventsourcedbanking.dao.writing.BalanceEventJsonMarshaller;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.settings.Settings;
import org.bson.Document;

import static io.github.tjheslin1.eventsourcedbanking.dao.MongoOperations.collectionCreateIfNotExistsForDatabase;

public class EventReader<T extends BalanceEvent> {

    private final MongoClient mongoClient;
    private Settings settings;

    public EventReader(MongoClient mongoClient, Settings settings) {
        this.mongoClient = mongoClient;
        this.settings = settings;
    }

    // TODO get the database here or pass in?
    public T read(BalanceEvent balanceEvent, BalanceEventJsonUnmarshaller jsonUnmarshaller) {
        MongoDatabase eventStoreDb = mongoClient.getDatabase(settings.mongoDbName());

//        eventStoreDb.
        return null;
    }
}
