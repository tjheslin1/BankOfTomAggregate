package io.github.tjheslin1.aggregate.application;

import io.github.tjheslin1.aggregate.domain.events.EventStore;

import java.time.LocalDateTime;

import static io.github.tjheslin1.aggregate.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.aggregate.application.cqrs.command.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand.withdrawFundsCommand;

public class BankingGateway {

    private final EventStore eventStore;

    public BankingGateway(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void depositFunds(int accountId, double amount, LocalDateTime timeOfCommand) throws Exception {
        eventStore.store(depositFundsCommand(accountId, amount, timeOfCommand), depositEventWiring());
    }

    public void withdrawFunds(int accountId, double amount, LocalDateTime timeOfCommand) throws Exception {
        eventStore.store(withdrawFundsCommand(accountId, amount, timeOfCommand), withdrawalEventWiring());
    }
}
