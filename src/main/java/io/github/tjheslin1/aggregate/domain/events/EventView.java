package io.github.tjheslin1.aggregate.domain.events;

import java.util.stream.Stream;

public interface EventView {
    Stream<BalanceCommand> eventsSortedByTime(int accountId, EventWiring eventWiring);
}
