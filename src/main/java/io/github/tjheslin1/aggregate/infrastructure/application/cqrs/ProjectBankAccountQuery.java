package io.github.tjheslin1.aggregate.infrastructure.application.cqrs;

import io.github.tjheslin1.aggregate.domain.banking.Balance;
import io.github.tjheslin1.aggregate.domain.banking.BankAccount;
import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.domain.events.BalanceEventVisitor;
import io.github.tjheslin1.aggregate.domain.events.EventSorter;

import java.util.List;
import java.util.stream.Stream;

public class ProjectBankAccountQuery {

    public static BankAccount projectBankAccountQuery(int accountId, EventSorter eventSorter, List<BalanceCommand>... balanceCommands)
            throws Exception {
        Stream<BalanceCommand> sortedCommands = eventSorter.sortedEventViews(balanceCommands);
        return new BankAccount(accountId, upToDateBalance(sortedCommands));
    }

    private static Balance upToDateBalance(Stream<BalanceCommand> balanceCommands) {
        BalanceEventVisitor balanceEventVisitor = new BalanceEventVisitor(new Balance());
        balanceCommands.forEach(event -> event.visit(balanceEventVisitor));
        return balanceEventVisitor.projectBalance();
    }
}
