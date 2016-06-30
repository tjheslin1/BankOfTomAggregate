package io.github.tjheslin1.esb.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;
import io.github.tjheslin1.esb.infrastructure.settings.TestSettings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionCreateIfNotExistsForDatabase;

public class MongoOperationsTest implements WithMockito, WithAssertions {

    private final String EXISTING_COLLECTION = "new_collection_1";
    private final String NON_EXISTENT_COLLECTION = "new_collection_2";

    private MongoSettings mongoSettings = new TestSettings();
    private MongoClient mongoClient = new MongoClient("localhost", mongoSettings.mongoDbPort());
    private MongoDatabase mongoDb = mongoClient.getDatabase(mongoSettings.mongoDbName());

    @Before
    public void before() {
        mongoDb.createCollection(EXISTING_COLLECTION);
    }

    @After
    public void after() {
        mongoDb.getCollection(EXISTING_COLLECTION).drop();
        mongoDb.getCollection(NON_EXISTENT_COLLECTION).drop();
    }

    @Test
    public void retrieveExistingCollectionTest() {
        MongoCollection<Document> existingCollection = collectionCreateIfNotExistsForDatabase(EXISTING_COLLECTION, mongoDb);
        assertThat(existingCollection.getNamespace()).isEqualTo(new MongoNamespace("events_store.new_collection_1"));
    }

    @Test
    public void createAndRetrieveNewCollectionTest() {
        MongoCollection<Document> existingCollection = collectionCreateIfNotExistsForDatabase(NON_EXISTENT_COLLECTION, mongoDb);
        assertThat(existingCollection.getNamespace()).isEqualTo(new MongoNamespace("events_store.new_collection_2"));
    }
}