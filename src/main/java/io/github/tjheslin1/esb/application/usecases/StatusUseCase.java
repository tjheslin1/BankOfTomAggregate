package io.github.tjheslin1.esb.application.usecases;

import io.github.tjheslin1.esb.application.probe.Probe;
import io.github.tjheslin1.esb.domain.Status;

import java.util.stream.Stream;

public class StatusUseCase {

    private final Stream<Probe> probes;

    public StatusUseCase(Stream<Probe> probes) {
        this.probes = probes;
    }

    public Status checkStatus() {
        return probes.anyMatch(probe -> Status.FAIL.equals(probe.probe())) ? Status.FAIL : Status.OK;
    }
}
