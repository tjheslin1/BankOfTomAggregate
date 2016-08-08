package io.github.tjheslin1.esb.infrastructure.application.web.status;

import io.github.tjheslin1.esb.domain.status.ProbeResult;

import java.util.List;

public class StatusPageResult {

    private final List<ProbeResult> probeResults;
    private final String overallResult;

    public StatusPageResult(List<ProbeResult> probeResults, String overallResult) {
        this.probeResults = probeResults;
        this.overallResult = overallResult;
    }

    public List<ProbeResult> probeResults() {
        return probeResults;
    }

    public String overallResult() {
        return overallResult;
    }
}
