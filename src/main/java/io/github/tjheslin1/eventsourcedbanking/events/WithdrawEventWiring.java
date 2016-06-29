package io.github.tjheslin1.eventsourcedbanking.events;

import io.github.tjheslin1.eventsourcedbanking.cqrs.command.BalanceEventJsonMarshaller;
import io.github.tjheslin1.eventsourcedbanking.cqrs.command.WithdrawFundsMarshaller;
import io.github.tjheslin1.eventsourcedbanking.cqrs.query.BalanceEventJsonUnmarshaller;
import io.github.tjheslin1.eventsourcedbanking.cqrs.query.WithdrawFundsUnmarshaller;

import static io.github.tjheslin1.eventsourcedbanking.cqrs.MongoOperations.collectionNameForEvent;

public class WithdrawEventWiring implements EventWiring {

    private static EventWiring instance;


    public static EventWiring withdrawalEventWiring() {
        if (instance == null) {
            instance = new WithdrawEventWiring();
        }

        return instance;
    }

    @Override
    public String collectionName() {
        return collectionNameForEvent(WithdrawFundsBalanceEvent.class);
    }

    @Override
    public BalanceEventJsonMarshaller marshaller() {
        return new WithdrawFundsMarshaller();
    }

    @Override
    public BalanceEventJsonUnmarshaller unmarshaller() {
        return new WithdrawFundsUnmarshaller();
    }
}
