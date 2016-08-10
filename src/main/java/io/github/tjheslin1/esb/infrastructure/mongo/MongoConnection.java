package io.github.tjheslin1.esb.infrastructure.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;

public class MongoConnection {

    /**
     * Used for Wiring and tests only.
     */
    public static MongoClient mongoClient(MongoSettings mongoSettings) {
        MongoClientOptions.Builder mongoClientBuilder = MongoClientOptions.builder()
                .connectTimeout(mongoSettings.connectTimeout())
                .maxWaitTime(mongoSettings.maxWaitTime());

        return new MongoClient(new ServerAddress("172.17.0.2", mongoSettings.mongoDbPort()), mongoClientBuilder.build());
    }
}
