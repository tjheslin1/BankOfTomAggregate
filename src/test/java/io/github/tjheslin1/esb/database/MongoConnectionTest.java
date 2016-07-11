package io.github.tjheslin1.esb.database;

import com.mongodb.MongoClient;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.infrastructure.mongo.MongoConnection;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class MongoConnectionTest implements WithAssertions, WithMockito {

    private MongoSettings settings = mock(Settings.class);
    private MongoConnection mongoConnection = new MongoConnection(settings);

    @Test
    public void connectToDatabaseContainerTest() throws Exception {
        when(settings.mongoDbPort()).thenReturn(27017);
        MongoClient mongoClient = mongoConnection.connection();

        try {
            // TODO getAddress() takes 30 secs to detect container is not running - execute docker ps somehow?
            assertThat(mongoClient.getAddress()).isNotNull();
        } catch (Exception e) {
            fail("Failed to connect to mongo database.\nIs the container running?");
        }
    }
}