package acceptance;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class MongoOperationsTest implements WithMockito, WithAssertions {

    private final String EXISTING_COLLECTION = "new_collection_1";
    private final String NON_EXISTENT_COLLECTION = "new_collection_2";

//    private MongoDatabase mongoDb = new

    @Test
    public void retrieveExistingCollectionTest() {
//        when(mongoDb.listCollectionNames()).thenReturn(collectionNames);
//        collectionCreateIfNotExistsForDatabase(EXISTING_COLLECTION, mongoDb)
    }

    @Test
    public void createAndRetrieveNewCollectionTest() {

    }
}