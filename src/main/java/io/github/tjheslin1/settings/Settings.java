package io.github.tjheslin1.settings;

public class Settings {

    private PropertiesReader propertiesReader;

    public Settings(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    public int mongoDbPort() {
        return Integer.parseInt(propertiesReader.readProperty("mongo.db.port"));
    }
}
