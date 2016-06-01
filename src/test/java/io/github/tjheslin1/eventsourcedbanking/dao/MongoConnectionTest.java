package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.MongoClient;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

public class MongoConnectionTest implements WithMockito, WithAssertions {

    public static final String DATABASE_NAME = "events_store";

    private Settings settings = mock(Settings.class);
    private MongoClient mongo;


    @Before
    public void before() {
        when(settings.mongoDbPort()).thenReturn(27017);
        mongo = new MongoClient("localhost", settings.mongoDbPort());
    }

    @Test
    public void connectToDatabaseContainerTest() {
        // TODO test this
    }
}