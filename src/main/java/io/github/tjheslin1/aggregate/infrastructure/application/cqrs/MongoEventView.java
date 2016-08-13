package io.github.tjheslin1.aggregate.infrastructure.application.cqrs;

import io.github.tjheslin1.aggregate.application.cqrs.query.BalanceQueryReader;
import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.domain.events.EventView;
import io.github.tjheslin1.aggregate.domain.events.EventWiring;

import java.util.function.Function;
import java.util.stream.Stream;

public class MongoEventView implements EventView {

    private BalanceQueryReader queryReader;

    public MongoEventView(BalanceQueryReader queryReader) {
        this.queryReader = queryReader;
    }

    @Override
    public Stream<BalanceCommand> eventsSortedByTime(int accountId, EventWiring... eventWirings) {
        return Stream.of(eventWirings).flatMap(toBalanceEventStream(accountId)).sorted();
    }

    private Function<EventWiring, Stream<BalanceCommand>> toBalanceEventStream(int accountId) {
        return eventWiring -> specifiedEventsForAccountId(accountId, eventWiring);
    }

    private Stream<BalanceCommand> specifiedEventsForAccountId(int accountId, EventWiring eventWiring) {
        return queryReader.retrieveSortedEvents(accountId, eventWiring);
    }
}
