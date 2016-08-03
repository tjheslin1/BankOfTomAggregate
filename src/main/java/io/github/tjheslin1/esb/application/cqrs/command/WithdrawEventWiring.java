package io.github.tjheslin1.esb.application.cqrs.command;

import io.github.tjheslin1.esb.application.cqrs.query.EventJsonUnmarshaller;
import io.github.tjheslin1.esb.domain.events.EventWiring;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsMarshaller;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsUnmarshaller;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionNameForEvent;

public class WithdrawEventWiring implements EventWiring {

    private static WithdrawEventWiring instance;


    public static WithdrawEventWiring withdrawalEventWiring() {
        if (instance == null) {
            instance = new WithdrawEventWiring();
        }

        return instance;
    }

    @Override
    public String collectionName() {
        return collectionNameForEvent(WithdrawFundsCommand.class);
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
