package io.github.tjheslin1.esb.infrastructure.domain.status;

import io.github.tjheslin1.esb.domain.status.ProbeResult;
import io.github.tjheslin1.esb.domain.status.Status;

public class MongoProbeResult implements ProbeResult {

    private final Status result;

    public MongoProbeResult(Status result) {
        this.result = result;
    }

    @Override
    public Status result() {
        return result;
    }

    @Override
    public String toJson() {
        return "";
    }
}
