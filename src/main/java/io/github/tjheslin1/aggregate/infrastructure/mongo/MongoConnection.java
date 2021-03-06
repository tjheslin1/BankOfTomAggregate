package io.github.tjheslin1.aggregate.infrastructure.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;

public class MongoConnection {

    /**
     * Used for Wiring and tests only.
     */
    public static MongoClient mongoClient(Settings settings) {
        MongoClientOptions.Builder mongoClientBuilder = MongoClientOptions.builder()
                .connectTimeout(settings.connectTimeout())
                .maxWaitTime(settings.maxWaitTime());

        return new MongoClient(new ServerAddress(settings.host(), settings.mongoDbPort()), mongoClientBuilder.build());
    }
}
