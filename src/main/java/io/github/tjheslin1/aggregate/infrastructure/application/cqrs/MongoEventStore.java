package io.github.tjheslin1.aggregate.infrastructure.application.cqrs;

import io.github.tjheslin1.aggregate.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.domain.events.EventStore;
import io.github.tjheslin1.aggregate.domain.events.EventWiring;

public class MongoEventStore implements EventStore {

    private BalanceCommandWriter commandWriter;

    public MongoEventStore(BalanceCommandWriter commandWriter) {
        this.commandWriter = commandWriter;
    }

    @Override
    public void store(BalanceCommand balanceCommand, EventWiring eventWiring) throws Exception {
        commandWriter.write(balanceCommand, eventWiring);
    }
}
