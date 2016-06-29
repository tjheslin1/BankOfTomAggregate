package io.github.tjheslin1.eventsourcedbanking.events;

import io.github.tjheslin1.eventsourcedbanking.cqrs.command.BalanceEventJsonMarshaller;
import io.github.tjheslin1.eventsourcedbanking.cqrs.query.BalanceEventJsonUnmarshaller;

public interface EventWiring {

    String collectionName();
    BalanceEventJsonMarshaller marshaller();
    BalanceEventJsonUnmarshaller unmarshaller();
}
