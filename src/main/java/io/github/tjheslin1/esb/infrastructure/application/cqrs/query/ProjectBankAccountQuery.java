package io.github.tjheslin1.esb.infrastructure.application.cqrs.query;

import io.github.tjheslin1.esb.domain.Balance;
import io.github.tjheslin1.esb.domain.BankAccount;
import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.BalanceEventVisitor;
import io.github.tjheslin1.esb.domain.events.EventStore;

import java.util.stream.Stream;

import static io.github.tjheslin1.esb.application.eventwiring.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.eventwiring.WithdrawEventWiring.withdrawalEventWiring;

public class ProjectBankAccountQuery {

    public static BankAccount projectBankAccountQuery(int accountId, EventStore eventStore) {
        Stream<BalanceCommand> balanceCommands = eventStore.eventsSortedByTime(accountId, depositEventWiring(), withdrawalEventWiring());
        return new BankAccount(accountId, upToDateBalance(balanceCommands));
    }

    private static Balance upToDateBalance(Stream<BalanceCommand> balanceCommands) {
        BalanceEventVisitor balanceEventVisitor = new BalanceEventVisitor(new Balance());
        balanceCommands.forEach(event -> event.visit(balanceEventVisitor));
        return balanceEventVisitor.projectBalance();
    }
}
