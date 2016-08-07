package io.github.tjheslin1.esb.infrastructure.application.cqrs;

import io.github.tjheslin1.esb.domain.banking.Balance;
import io.github.tjheslin1.esb.domain.banking.BankAccount;
import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.BalanceEventVisitor;
import io.github.tjheslin1.esb.domain.events.EventView;

import java.util.stream.Stream;

import static io.github.tjheslin1.esb.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.cqrs.command.WithdrawEventWiring.withdrawalEventWiring;

public class ProjectBankAccountQuery {

    public static BankAccount projectBankAccountQuery(int accountId, EventView eventView) throws Exception {
        Stream<BalanceCommand> balanceCommands = eventView.eventsSortedByTime(accountId, depositEventWiring(), withdrawalEventWiring());
        return new BankAccount(accountId, upToDateBalance(balanceCommands));
    }

    private static Balance upToDateBalance(Stream<BalanceCommand> balanceCommands) {
        BalanceEventVisitor balanceEventVisitor = new BalanceEventVisitor(new Balance());
        balanceCommands.forEach(event -> event.visit(balanceEventVisitor));
        return balanceEventVisitor.projectBalance();
    }
}
