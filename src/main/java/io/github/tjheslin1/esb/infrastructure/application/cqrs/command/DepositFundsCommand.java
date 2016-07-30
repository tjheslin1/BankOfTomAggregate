package io.github.tjheslin1.esb.infrastructure.application.cqrs.command;

import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.EventVisitor;

import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.eventDatePattern;

public class DepositFundsCommand extends BalanceCommand {

    private DepositFundsCommand(int accountId, double amount, LocalDateTime timeOfEvent) {
        super(accountId, amount, timeOfEvent);
    }

    public static DepositFundsCommand depositFundsCommand(int accountId, double amount, LocalDateTime timeOfCommand) {
        return new DepositFundsCommand(accountId, amount, timeOfCommand);
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.consider(this);
    }
}
