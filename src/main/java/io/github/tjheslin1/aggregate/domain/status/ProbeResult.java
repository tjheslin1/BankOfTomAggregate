package io.github.tjheslin1.aggregate.domain.status;

public interface ProbeResult {

    Status result();
    String toJson();
}
