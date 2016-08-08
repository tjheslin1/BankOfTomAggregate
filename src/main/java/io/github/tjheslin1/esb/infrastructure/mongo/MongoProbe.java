package io.github.tjheslin1.esb.infrastructure.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import io.github.tjheslin1.esb.application.probe.Probe;
import io.github.tjheslin1.esb.domain.status.ProbeResult;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;
import org.bson.Document;

public class MongoProbe implements Probe {

    private final MongoClient mongoClient;
    private final MongoSettings settings;

    public MongoProbe(MongoClient mongoClient, MongoSettings settings) {
        this.mongoClient = mongoClient;
        this.settings = settings;
    }

    @Override
    public ProbeResult probe() {
        Document ping = mongoClient.getDatabase(settings.mongoDbName()).runCommand(new BasicDBObject("ping", 1));

        String dbStatus = ping.containsKey("ok") ? "OK" : "FAIL";
        return new MongoProbeResult(dbStatus);
    }
}
