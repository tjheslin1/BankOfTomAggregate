package io.github.tjheslin1.esb.infrastructure.mongo;

import io.github.tjheslin1.esb.domain.status.ProbeResult;
import io.github.tjheslin1.esb.domain.status.Status;

import static java.lang.String.format;

public class MongoProbeResult implements ProbeResult {

    private static final String PROBE_FORMAT = "{\"dbStatus\": \"%s\"}";

    private final String dbStatus;

    public MongoProbeResult(String dbStatus) {
        this.dbStatus = dbStatus;
    }

    @Override
    public Status result() {
        return dbStatus.equalsIgnoreCase("ok") ? Status.OK : Status.FAIL;
    }

    @Override
    public String toJson() {
        return format(PROBE_FORMAT, dbStatus.toUpperCase());
    }
}
