package io.github.tjheslin1.esb.infrastructure.settings;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.Properties;

public class SettingsTest implements WithAssertions, WithMockito {

    @Test
    public void settingsFileReadFrom() throws Exception {
        Properties properties = mock(Properties.class);
        Settings settings = new Settings(properties);

        when(properties.getProperty("mongo.db.port")).thenReturn("1");

        assertThat(settings.mongoDbPort()).isGreaterThan(0);
    }
}