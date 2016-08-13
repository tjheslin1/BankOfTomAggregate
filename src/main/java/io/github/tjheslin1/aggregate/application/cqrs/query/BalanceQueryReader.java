package io.github.tjheslin1.aggregate.application.cqrs.query;

import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.domain.events.EventWiring;

import java.util.stream.Stream;

public interface BalanceQueryReader {
    Stream<BalanceCommand> retrieveSortedEvents(int accountId, EventWiring eventWiring);
}
