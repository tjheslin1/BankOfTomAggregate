package io.github.tjheslin1.esb.database;

import com.mongodb.MongoClient;
import io.github.tjheslin1.esb.infrastructure.mongo.MongoConnection;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;
import io.github.tjheslin1.esb.infrastructure.settings.TestSettings;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class MongoConnectionTest implements WithAssertions {

    private MongoSettings mongoSettings = new TestSettings();
    private MongoConnection mongoConnection = new MongoConnection(mongoSettings);

    @Test
    public void connectToDatabaseContainerTest() throws Exception {
        MongoClient mongoClient = mongoConnection.connection();

        try {
            // TODO getAddress() takes 30 secs to detect container is not running - execute docker ps somehow?
            assertThat(mongoClient.getAddress()).isNotNull();
        } catch (Exception e) {
            fail("Failed to connect to mongo database.\nIs the container running?");
        }
    }
}