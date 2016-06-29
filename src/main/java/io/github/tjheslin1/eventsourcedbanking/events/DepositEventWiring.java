package io.github.tjheslin1.eventsourcedbanking.events;

import io.github.tjheslin1.eventsourcedbanking.cqrs.command.BalanceEventJsonMarshaller;
import io.github.tjheslin1.eventsourcedbanking.cqrs.command.DepositFundsMarshaller;
import io.github.tjheslin1.eventsourcedbanking.cqrs.query.BalanceEventJsonUnmarshaller;
import io.github.tjheslin1.eventsourcedbanking.cqrs.query.DepositFundsUnmarshaller;

import static io.github.tjheslin1.eventsourcedbanking.cqrs.MongoOperations.collectionNameForEvent;

public class DepositEventWiring implements EventWiring {

    private static EventWiring instance;

    public static EventWiring depositEventWiring() {
        if (instance == null) {
            instance = new DepositEventWiring();
        }

        return instance;
    }

    @Override
    public String collectionName() {
        return collectionNameForEvent(DepositFundsBalanceEvent.class);
    }

    @Override
    public BalanceEventJsonMarshaller marshaller() {
        return new DepositFundsMarshaller();
    }

    @Override
    public BalanceEventJsonUnmarshaller unmarshaller() {
        return new DepositFundsUnmarshaller();
    }
}
