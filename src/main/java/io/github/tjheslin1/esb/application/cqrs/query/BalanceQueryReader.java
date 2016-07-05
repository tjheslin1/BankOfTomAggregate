package io.github.tjheslin1.esb.application.cqrs.query;

import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.EventWiring;

import java.util.stream.Stream;

public interface BalanceQueryReader {
    Stream<BalanceCommand> retrieveSortedEvents(int accountId, EventWiring eventWiring);
}
