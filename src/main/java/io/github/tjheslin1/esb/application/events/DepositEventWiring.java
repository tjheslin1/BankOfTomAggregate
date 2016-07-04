package io.github.tjheslin1.esb.application.events;

import io.github.tjheslin1.esb.application.cqrs.command.EventJsonMarshaller;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsMarshaller;
import io.github.tjheslin1.esb.application.cqrs.query.EventJsonUnmarshaller;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.query.DepositFundsUnmarshaller;
import io.github.tjheslin1.esb.domain.events.EventWiring;
import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsEvent;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionNameForEvent;

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
        return collectionNameForEvent(DepositFundsEvent.class);
    }

    @Override
    public EventJsonMarshaller marshaller() {
        return new DepositFundsMarshaller();
    }

    @Override
    public EventJsonUnmarshaller unmarshaller() {
        return new DepositFundsUnmarshaller();
    }
}
