package io.github.tjheslin1.settings;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReaderTest implements WithAssertions, WithMockito {

    @Test
    public void readPropertiesFile() throws Exception {
        Properties properties = mock(Properties.class);
        InputStream inputStream = mock(InputStream.class);

        new PropertiesReader(properties, inputStream);

        verify(properties).load(inputStream);
    }

    @Test
    public void readProperty() throws Exception {
        Properties properties = mock(Properties.class);
        InputStream inputStream = mock(InputStream.class);

        when(properties.getProperty("key")).thenReturn("value");

        PropertiesReader propertiesReader = new PropertiesReader(properties, inputStream);

        assertThat(propertiesReader.readProperty("key")).isEqualTo("value");
    }
}