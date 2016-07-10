package io.github.tjheslin1.esb.domain.events;

public interface EventStore {
    void store(BalanceCommand balanceCommand, EventWiring eventWiring) throws Exception;
}
