package io.github.tjheslin1.esb.infrastructure.application.cqrs.command;

import io.github.tjheslin1.esb.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.EventStore;
import io.github.tjheslin1.esb.domain.events.EventWiring;

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
