package io.github.tjheslin1.aggregate.infrastructure.settings;

public interface MongoSettings {

    int mongoDbPort();
    String mongoDbName();
    int connectTimeout();
    int maxWaitTime();
}
