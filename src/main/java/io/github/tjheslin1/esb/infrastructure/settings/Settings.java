package io.github.tjheslin1.esb.infrastructure.settings;

public class Settings implements MongoSettings, ServerSettings {

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

    @Override
    public String webProtocol() {
        return propertiesReader.readProperty("web.protocol");
    }

    @Override
    public String host() {
        return propertiesReader.readProperty("host");
    }

    @Override
    public int serverPort() {
        return Integer.parseInt(propertiesReader.readProperty("server.port"));
    }
}
