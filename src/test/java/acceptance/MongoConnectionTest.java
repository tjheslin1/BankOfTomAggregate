package acceptance;

import com.mongodb.MongoClient;
import io.github.tjheslin1.settings.PropertiesReader;
import io.github.tjheslin1.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

public class MongoConnectionTest implements WithAssertions {

    public static final String DATABASE_NAME = "events_store";

    private Settings settings = new Settings(new PropertiesReader("localhost"));
    private MongoClient mongoClient;

    @Before
    public void before() {
        mongoClient = new MongoClient("localhost", settings.mongoDbPort());
    }

    @Test
    public void connectToDatabaseContainerTest() {
        mongoClient.getDatabase(settings.mongoDbName());
        fail("");
    }
}