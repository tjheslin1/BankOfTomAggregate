package io.github.tjheslin1.esb.infrastructure.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

public class PropertiesReader {

    private final String environment;
    private Properties properties;

    public PropertiesReader(String environment) {
        this.environment = environment;
        this.properties = new Properties();

        loadProperties();
    }

    public String readProperty(String key) {
        return properties.getProperty(key);
    }

    private void loadProperties() {
        try (InputStream inputStream = new FileInputStream(propertiesFileName())) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read file: " + propertiesFileName());
        }
    }

    private String propertiesFileName() {
        return format("src/main/resources/%s.properties", environment);
    }
}
