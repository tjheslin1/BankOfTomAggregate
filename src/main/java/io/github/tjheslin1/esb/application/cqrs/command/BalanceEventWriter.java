package io.github.tjheslin1.esb.application.cqrs.command;

import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.domain.events.EventWiring;

public interface BalanceEventWriter {

    void write(BalanceEvent balanceEvent, EventWiring eventWiring);
}
