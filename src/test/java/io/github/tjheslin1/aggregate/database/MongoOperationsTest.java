package io.github.tjheslin1.aggregate.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.github.tjheslin1.aggregate.infrastructure.mongo.MongoOperations.collectionCreateIfNotExistsForDatabase;

public class MongoOperationsTest implements WithMockito, WithAssertions {

    private final String EXISTING_COLLECTION = "new_collection_1";
    private final String NON_EXISTENT_COLLECTION = "new_collection_2";

    private MongoClient mongoClient;
    private MongoDatabase eventStoreDb;
    private MongoDatabase eventStoreDbSpy;

    @Before
    public void before() {
        mongoClient = new MongoClient("localhost", 27017);
        eventStoreDb = mongoClient.getDatabase("events_store");

        eventStoreDb.createCollection(EXISTING_COLLECTION);
        eventStoreDbSpy = spy(eventStoreDb);
    }

    @After
    public void after() {
        eventStoreDb.getCollection(EXISTING_COLLECTION).drop();
        eventStoreDb.getCollection(NON_EXISTENT_COLLECTION).drop();
    }

    @Test
    public void retrieveExistingCollectionTest() {
        MongoCollection<Document> existingCollection = collectionCreateIfNotExistsForDatabase(EXISTING_COLLECTION, eventStoreDbSpy);
        assertThat(existingCollection.getNamespace()).isEqualTo(new MongoNamespace("events_store.new_collection_1"));

        verify(eventStoreDbSpy).getCollection(EXISTING_COLLECTION);
        verify(eventStoreDbSpy, times(0)).createCollection(EXISTING_COLLECTION);
    }

    @Test
    public void createAndRetrieveNewCollectionTest() {
        MongoCollection<Document> existingCollection = collectionCreateIfNotExistsForDatabase(NON_EXISTENT_COLLECTION, eventStoreDbSpy);
        assertThat(existingCollection.getNamespace()).isEqualTo(new MongoNamespace("events_store.new_collection_2"));

        verify(eventStoreDbSpy).createCollection(NON_EXISTENT_COLLECTION);
    }
}