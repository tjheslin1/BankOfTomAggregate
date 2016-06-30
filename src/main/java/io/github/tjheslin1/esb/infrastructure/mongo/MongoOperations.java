package io.github.tjheslin1.esb.infrastructure.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.format.DateTimeFormatter;
import java.util.stream.StreamSupport;

public class MongoOperations {

    public static String collectionNameForEvent(Class c) {
        return c.getSimpleName();
    }

    public static DateTimeFormatter eventDatePattern() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS");
    }

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
