package io.github.tjheslin1.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private Properties properties;
    private InputStream inputStream;

    public PropertiesReader(Properties properties, InputStream inputStream) {
        this.properties = properties;
        this.inputStream = inputStream;

        loadProperties();
    }

    public String readProperty(String key) {
        return properties.getProperty(key);
    }

    private void loadProperties() {
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
