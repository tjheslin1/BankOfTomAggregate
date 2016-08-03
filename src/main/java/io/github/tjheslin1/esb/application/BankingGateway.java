package io.github.tjheslin1.esb.application;

import io.github.tjheslin1.esb.domain.events.EventStore;

import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.cqrs.command.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand.withdrawFundsCommand;

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
