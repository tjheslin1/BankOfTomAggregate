package io.github.tjheslin1.esb.application.cqrs.command;

import io.github.tjheslin1.esb.application.cqrs.query.EventJsonUnmarshaller;
import io.github.tjheslin1.esb.domain.events.EventWiring;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.deposit.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.deposit.DepositFundsMarshaller;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.deposit.DepositFundsUnmarshaller;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionNameForEvent;

public class DepositEventWiring implements EventWiring {

    private static DepositEventWiring instance;

    public static DepositEventWiring depositEventWiring() {
        if (instance == null) {
            instance = new DepositEventWiring();
        }

        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }

    @Override
    public String collectionName() {
        return collectionNameForEvent(DepositFundsCommand.class);
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
