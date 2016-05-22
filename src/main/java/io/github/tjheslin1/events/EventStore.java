package io.github.tjheslin1.events;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static io.github.tjheslin1.events.EventIdGenerator.generateEventId;

public class EventStore {

    private static EventStore singleton;
    private static LinkedHashMap<String, Event> events;

    private EventStore() {

    }

    public static EventStore eventStore() {
        return singleton == null ? new EventStore() : singleton;
    }

    public static void addEvent(Event event) throws Exception {
        events.put(generateEventId(7), event);
    }

    public static List eventsForBankAccountWithId(int bankAccountId) {
        return  null;
    }
}
