package io.github.tjheslin1.esb.application;

import io.github.tjheslin1.esb.domain.BankAccount;
import io.github.tjheslin1.esb.domain.events.EventStore;
import io.github.tjheslin1.esb.domain.events.EventView;

import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.cqrs.command.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand.withdrawFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.query.ProjectBankAccountQuery.projectBankAccountQuery;

public class BankingGateway {

    private final EventStore eventStore;
    private final EventView eventView;

    public BankingGateway(EventStore eventStore, EventView eventView) {
        this.eventStore = eventStore;
        this.eventView = eventView;
    }

    public void depositFunds(int accountId, double amount, LocalDateTime now) {
        eventStore.store(depositFundsCommand(accountId, amount, now), depositEventWiring());
    }

    public void withdrawFunds(int accountId, double amount, LocalDateTime now) {
        eventStore.store(withdrawFundsCommand(accountId, amount, now), withdrawalEventWiring());
    }

    public BankAccount projectMyBankAccount(int accountId) {
        return projectBankAccountQuery(accountId, eventView);
    }
}
