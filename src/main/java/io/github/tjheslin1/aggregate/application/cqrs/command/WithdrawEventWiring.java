package io.github.tjheslin1.aggregate.application.cqrs.command;

import io.github.tjheslin1.aggregate.application.cqrs.query.EventJsonUnmarshaller;
import io.github.tjheslin1.aggregate.domain.events.EventWiring;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsMarshaller;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsUnmarshaller;

import static io.github.tjheslin1.aggregate.infrastructure.mongo.MongoOperations.collectionNameForEvent;

public class WithdrawEventWiring implements EventWiring {

    private static volatile WithdrawEventWiring instance;

    public static synchronized WithdrawEventWiring withdrawalEventWiring() {
        if (instance == null) {
            instance = new WithdrawEventWiring();
        }

        return instance;
    }

    /**
     * For testing
     */
    public static void clearInstance() {
        instance = null;
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
