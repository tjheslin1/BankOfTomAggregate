package io.github.tjheslin1.aggregate.application.probe;

import io.github.tjheslin1.aggregate.domain.status.ProbeResult;

public interface Probe {

    ProbeResult probe();
}
