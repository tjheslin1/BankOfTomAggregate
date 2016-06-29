package io.github.tjheslin1.eventsourcedbanking.database;

import com.mongodb.MongoClient;
import io.github.tjheslin1.eventsourcedbanking.cqrs.MongoConnection;
import io.github.tjheslin1.settings.MongoSettings;
import io.github.tjheslin1.settings.TestSettings;
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
            fail("Failed to connect to mongo io.github.tjheslin1.eventsourcedbanking.database.\nIs the container running?");
        }
    }
}