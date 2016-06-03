package acceptance;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import static io.github.tjheslin1.eventsourcedbanking.dao.MongoOperations.collectionCreateIfNotExistsForDatabase;

public class MongoOperationsTest implements WithMockito, WithAssertions {

    private final String EXISTING_COLLECTION = "new_collection_1";
    private final String NON_EXISTENT_COLLECTION = "new_collection_2";

    private MongoDatabase mongoDb;
    private Settings settings = new Settings();

    @Before
    public void before() {
        MongoClient mongoClient = new MongoClient("localhost", settings.mongoDbPort());
        mongoDb = mongoClient.getDatabase(settings.mongoDbName());
    }

    @Test
    public void retrieveExistingCollectionTest() {
        collectionCreateIfNotExistsForDatabase(EXISTING_COLLECTION, mongoDb);
    }

    @Test
    public void createAndRetrieveNewCollectionTest() {

    }
}