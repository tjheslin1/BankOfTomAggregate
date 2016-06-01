package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.stream.StreamSupport;

public class MongoOperations {

    public static MongoCollection<Document> collectionCreateIfNotExistsForDatabase(String collectionName, MongoDatabase eventStoreDb) {
        if (!collectionExistsInDatabase(collectionName, eventStoreDb)) {
            eventStoreDb.createCollection(collectionName);
        }
        return eventStoreDb.getCollection(collectionName);
    }

    private static boolean collectionExistsInDatabase(String collectionName, MongoDatabase eventStoreDb) {
        return StreamSupport.stream(eventStoreDb.listCollectionNames().spliterator(), false)
                .filter(collName -> collName.equals(collectionName)).findFirst().isPresent();
    }
}
