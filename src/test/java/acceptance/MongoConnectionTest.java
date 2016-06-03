package acceptance;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;
import io.github.tjheslin1.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class MongoConnectionTest implements WithAssertions {

    private Settings settings = new Settings();

    @Test
    public void connectToDatabaseContainerTest() throws Exception {
        Builder o = MongoClientOptions.builder().connectTimeout(1000);
        MongoClient mongo = new MongoClient(new ServerAddress("localhost", settings.mongoDbPort()), o.build());

        try {
            assertThat(mongo.getAddress()).isNotNull();
        } catch (Exception e) {
            fail("Failed to connect to mongo database.\nIs the container running?");
        }
    }
}