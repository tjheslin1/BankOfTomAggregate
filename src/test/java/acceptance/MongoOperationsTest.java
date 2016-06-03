package acceptance;

import com.mongodb.MongoClient;
import com.mongodb.MongoNamespace;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.settings.Settings;
import io.github.tjheslin1.settings.TestSettings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.github.tjheslin1.eventsourcedbanking.dao.MongoOperations.collectionCreateIfNotExistsForDatabase;

public class MongoOperationsTest implements WithMockito, WithAssertions {

    private final String EXISTING_COLLECTION = "new_collection_1";
    private final String NON_EXISTENT_COLLECTION = "new_collection_2";

    private Settings settings = new TestSettings();
    private MongoClient mongoClient = new MongoClient("localhost", settings.mongoDbPort());
    private MongoDatabase mongoDb = mongoClient.getDatabase(settings.mongoDbName());

    @Before
    public void before() {
        mongoDb.createCollection(EXISTING_COLLECTION);
    }

    @After
    public void after() {
        mongoDb.getCollection(EXISTING_COLLECTION).drop();
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