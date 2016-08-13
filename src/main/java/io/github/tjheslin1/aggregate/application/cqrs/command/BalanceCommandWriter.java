package io.github.tjheslin1.aggregate.application.cqrs.command;

import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.domain.events.EventWiring;

public interface BalanceCommandWriter {

    void write(BalanceCommand balanceCommand, EventWiring eventWiring) throws Exception;
}
