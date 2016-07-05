package io.github.tjheslin1.esb.infrastructure.application;

import io.github.tjheslin1.esb.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.esb.application.cqrs.query.BalanceQueryReader;
import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.EventStore;
import io.github.tjheslin1.esb.domain.events.EventWiring;

import java.util.function.Function;
import java.util.stream.Stream;

public class MongoEventStore implements EventStore {

    private BalanceCommandWriter commandWriter;
    private BalanceQueryReader commandReader;

    public MongoEventStore(BalanceCommandWriter commandWriter, BalanceQueryReader commandReader) {
        this.commandWriter = commandWriter;
        this.commandReader = commandReader;
    }

    @Override
    public void store(BalanceCommand balanceCommand, EventWiring eventWiring) {
        commandWriter.write(balanceCommand, eventWiring);
    }

    @Override
    public Stream<BalanceCommand> eventsSortedByTime(int accountId, EventWiring... eventWirings) {
        return Stream.of(eventWirings).flatMap(toBalanceEventStream(accountId)).sorted();
    }

    private Function<EventWiring, Stream<BalanceCommand>> toBalanceEventStream(int accountId) {
        return eventWiring -> specifiedEventsForAccountId(accountId, eventWiring);
    }

    private Stream<BalanceCommand> specifiedEventsForAccountId(int accountId, EventWiring eventWiring) {
        return commandReader.retrieveSortedEvents(accountId, eventWiring);
    }
}
