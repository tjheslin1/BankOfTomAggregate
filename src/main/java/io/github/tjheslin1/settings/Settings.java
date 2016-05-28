package io.github.tjheslin1.settings;

import java.io.FileNotFoundException;

public class Settings {

    private PropertiesReader propertiesReader;

    public Settings(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    // TODO bad to throw Exception here?
    public int mongoDbPort() throws FileNotFoundException {
        return Integer.parseInt(propertiesReader.readProperty("mongo.db.port"));
    }

}
