package io.github.tjheslin1.esb.application.usecases;

import io.github.tjheslin1.esb.application.probe.Probe;
import io.github.tjheslin1.esb.domain.status.ProbeResult;

import java.util.stream.Stream;

public class StatusUseCase {

    private final Stream<Probe> probes;

    public StatusUseCase(Stream<Probe> probes) {
        this.probes = probes;
    }

    public Stream<ProbeResult> checkStatusProbes() {
        return probes.map(Probe::probe);
    }
}
