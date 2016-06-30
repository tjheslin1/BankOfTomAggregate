package io.github.tjheslin1.esb.application.events;

import io.github.tjheslin1.esb.domain.cqrs.command.EventJsonMarshaller;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsMarshaller;
import io.github.tjheslin1.esb.domain.cqrs.query.EventJsonUnmarshaller;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.query.WithdrawFundsUnmarshaller;
import io.github.tjheslin1.esb.domain.events.EventWiring;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionNameForEvent;

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
    public EventJsonMarshaller marshaller() {
        return new WithdrawFundsMarshaller();
    }

    @Override
    public EventJsonUnmarshaller unmarshaller() {
        return new WithdrawFundsUnmarshaller();
    }
}
