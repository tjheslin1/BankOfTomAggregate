package io.github.tjheslin1.eventsourcedbanking.cqrs;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import io.github.tjheslin1.settings.MongoSettings;

public class MongoConnection {

    private MongoSettings mongoSettings;

    public MongoConnection(MongoSettings mongoSettings) {
        this.mongoSettings = mongoSettings;
    }

    public MongoClient connection() {
        MongoClientOptions.Builder mongoClientBuilder = MongoClientOptions.builder().connectTimeout(1000);
        return new MongoClient(new ServerAddress("localhost", mongoSettings.mongoDbPort()), mongoClientBuilder.build());
    }
}
