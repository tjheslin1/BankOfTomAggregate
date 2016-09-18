package io.github.tjheslin1.aggregate.domain.events;

import java.util.List;
import java.util.stream.Stream;

public interface EventSorter {

    Stream<BalanceCommand> sortedEventViews(List<BalanceCommand>... collectionEvents);
}
