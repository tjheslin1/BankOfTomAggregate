package io.github.tjheslin1.esb.application.cqrs.query;

import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.domain.events.EventWiring;

import java.util.stream.Stream;

public interface BalanceEventReader {
    Stream<BalanceEvent> retrieveSortedEvents(int accountId, EventWiring eventWiring);
}
