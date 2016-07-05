package io.github.tjheslin1.esb.domain.events;

import java.util.stream.Stream;

public interface EventStore {
    void store(BalanceCommand balanceCommand, EventWiring eventWiring);

    Stream<BalanceCommand> eventsSortedByTime(int accountId, EventWiring... eventWirings);
}
