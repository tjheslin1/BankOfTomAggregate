package io.github.tjheslin1.aggregate.infrastructure.domain.events;

import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.domain.events.EventSorter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class BalanceEventSorter implements EventSorter {

    @Override
    public Stream<BalanceCommand> sortedEventViews(List<BalanceCommand>... collectionEvents) {
        return Arrays.stream(collectionEvents)
                .flatMap(Collection::stream)
                .sorted();
    }
}
