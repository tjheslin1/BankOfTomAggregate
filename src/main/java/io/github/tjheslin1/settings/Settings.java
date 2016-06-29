package io.github.tjheslin1.settings;

public class Settings implements MongoSettings {

    private PropertiesReader propertiesReader;

    public Settings(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    @Override
    public int mongoDbPort() {
        return Integer.parseInt(propertiesReader.readProperty("mongo.db.port"));
    }

    @Override
    public String mongoDbName() {
        return propertiesReader.readProperty("mongo.db.name");
    }
}
