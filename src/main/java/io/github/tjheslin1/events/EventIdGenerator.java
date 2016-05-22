package io.github.tjheslin1.events;

import java.time.LocalDateTime;

public class EventIdGenerator {

    private EventIdGenerator() {

    }

    public static String generateEventId(int bankAccountId) {
        return LocalDateTime.now().toString() + bankAccountId;
    }
}
