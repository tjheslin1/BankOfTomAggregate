package io.github.tjheslin1.eventsourcedbanking.cqrs;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import io.github.tjheslin1.settings.Settings;

public class MongoConnection {

    private Settings settings;

    public MongoConnection(Settings settings) {
        this.settings = settings;
    }

    public MongoClient connection() {
        MongoClientOptions.Builder mongoClientBuilder = MongoClientOptions.builder().connectTimeout(1000);
        return new MongoClient(new ServerAddress("localhost", settings.mongoDbPort()), mongoClientBuilder.build());
    }
}
