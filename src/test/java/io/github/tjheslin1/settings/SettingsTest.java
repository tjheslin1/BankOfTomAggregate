package io.github.tjheslin1.settings;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class SettingsTest implements WithAssertions, WithMockito {

    @Test
    public void settingsFileReadFrom() throws Exception {
        PropertiesReader propertiesReader = mock(PropertiesReader.class);
        Settings settings = new Settings(propertiesReader);

        when(propertiesReader.readProperty("mongo.db.port")).thenReturn("1");

        assertThat(settings.mongoDbPort()).isGreaterThan(0);
    }
}