package io.github.tjheslin1.esb.infrastructure.application.cqrs.withdraw;

import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.EventVisitor;

import java.time.LocalDateTime;

public class WithdrawFundsCommand extends BalanceCommand {

    private WithdrawFundsCommand(int accountId, double amount, LocalDateTime timeOfEvent) {
        super(accountId, amount, timeOfEvent);
    }

    public static WithdrawFundsCommand withdrawFundsCommand(int accountId, double amount, LocalDateTime timeOfEvent) {
        return new WithdrawFundsCommand(accountId, amount, timeOfEvent);
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.consider(this);
    }
}
