package io.github.tjheslin1.esb.infrastructure.settings;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.Properties;

public class SettingsTest implements WithAssertions, WithMockito {

    @Test
    public void mongoDbPortTest() throws Exception {
        Properties properties = mock(Properties.class);
        Settings settings = new Settings(properties);

        when(properties.getProperty("mongo.db.port")).thenReturn("1");

        assertThat(settings.mongoDbPort()).isGreaterThan(0);
    }

    @Test
    public void mongoDbNameTest() throws Exception {
        Properties properties = mock(Properties.class);
        Settings settings = new Settings(properties);

        when(properties.getProperty("mongo.db.name")).thenReturn("name");

        assertThat(settings.mongoDbName()).isEqualTo("name");
    }

    @Test
    public void webProtocolTest() throws Exception {
        Properties properties = mock(Properties.class);
        Settings settings = new Settings(properties);

        when(properties.getProperty("web.protocol")).thenReturn("http");

        assertThat(settings.webProtocol()).isEqualTo("http");
    }

    @Test
    public void hostTest() throws Exception {
        Properties properties = mock(Properties.class);
        Settings settings = new Settings(properties);

        when(properties.getProperty("host")).thenReturn("localhost");

        assertThat(settings.host()).isEqualTo("localhost");
    }

    @Test
    public void serverPortTest() throws Exception {
        Properties properties = mock(Properties.class);
        Settings settings = new Settings(properties);

        when(properties.getProperty("server.port")).thenReturn("1000");

        assertThat(settings.serverPort()).isEqualTo(1000);
    }
}