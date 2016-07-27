package io.github.tjheslin1.esb.infrastructure.settings;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.io.InputStream;

public class PropertiesReaderTest implements WithAssertions, WithMockito {

    @Test
    public void readProperty() throws Exception {
        PropertiesReader propertiesReader = new PropertiesReader("localhost");

        assertThat(propertiesReader.readProperty("mongo.db.port")).isEqualTo("27017");
    }

    @Test
    public void blowsUpIfReaderCantReadFile() throws Exception {
        try {
            new PropertiesReader("badEnvironmentName");
            fail("Should have thrown exception, failing to read bad properties file.");
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo("Unable to read file: badEnvironmentName.properties");
        }
    }
}