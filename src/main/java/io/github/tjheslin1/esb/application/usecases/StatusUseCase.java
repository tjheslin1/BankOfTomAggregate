package io.github.tjheslin1.esb.application.usecases;

import io.github.tjheslin1.esb.application.probe.Probe;
import io.github.tjheslin1.esb.domain.status.ProbeResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StatusUseCase {

    private final List<Probe> probes;

    public StatusUseCase(List<Probe> probes) {
        this.probes = probes;
    }

    public List<ProbeResult> checkStatusProbes() {
        return probes.stream().map(Probe::probe).collect(toList());
    }
}
