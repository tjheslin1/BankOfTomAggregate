package io.github.tjheslin1.esb.database;

import com.mongodb.MongoClient;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoConnection.mongoClient;

public class MongoConnectionTest implements WithAssertions, WithMockito {

    private Settings settings = mock(Settings.class);

    @Test
    public void connectToDatabaseContainerTest() throws Exception {
        when(settings.mongoDbPort()).thenReturn(27017);
        when(settings.maxWaitTime()).thenReturn(1000);
        when(settings.connectTimeout()).thenReturn(1000);

        MongoClient mongoClient = mongoClient(settings);

        try {
            // TODO getAddress() takes 30 secs to detect container is not running - execute docker ps somehow?
            assertThat(mongoClient.getAddress()).isNotNull();
        } catch (Exception e) {
            fail("Failed to connect to mongo database.\nIs the container running?");
        }
    }
}