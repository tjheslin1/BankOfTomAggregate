package io.github.tjheslin1.aggregate.infrastructure.settings;

import java.util.Properties;

public class Settings implements MongoSettings, ServerSettings {

    private Properties properties;

    public Settings(Properties properties) {
        this.properties = properties;
    }

    @Override
    public int mongoDbPort() {
        return Integer.parseInt(properties.getProperty("mongo.db.port"));
    }

    @Override
    public String mongoDbName() {
        return properties.getProperty("mongo.db.name");
    }

    @Override
    public int connectTimeout() {
        return Integer.parseInt(properties.getProperty("mongo.connect.timeout"));
    }

    @Override
    public int maxWaitTime() {
        return Integer.parseInt(properties.getProperty("mongo.max.wait.time"));
    }

    @Override
    public String host() {
        return properties.getProperty("host");
    }

    @Override
    public int serverPort() {
        return Integer.parseInt(properties.getProperty("server.port"));
    }
}
