package io.github.tjheslin1.esb.infrastructure.application.cqrs.query;

import io.github.tjheslin1.esb.application.cqrs.query.BalanceEventReader;
import io.github.tjheslin1.esb.domain.Balance;
import io.github.tjheslin1.esb.domain.BankAccount;
import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.BalanceEventVisitor;
import io.github.tjheslin1.esb.domain.events.EventWiring;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.github.tjheslin1.esb.application.eventwiring.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.eventwiring.WithdrawEventWiring.withdrawalEventWiring;
import static java.util.stream.Collectors.toList;

public class ProjectBankAccountQuery {

    public static BankAccount projectBankAccountQuery(int accountId, BalanceEventReader balanceEventReader) {
        List<BalanceCommand> balanceCommands = sortedEvents(accountId, balanceEventReader, depositEventWiring(), withdrawalEventWiring());
        return new BankAccount(accountId, upToDateBalance(balanceCommands));
    }

    public static List<BalanceCommand> sortedEvents(int accountId, BalanceEventReader balanceEventReader, EventWiring... eventWirings) {
        return Stream.of(eventWirings).flatMap(toBalanceEventStream(accountId, balanceEventReader))
                .sorted()
                .collect(toList());
    }

    private static Function<EventWiring, Stream<BalanceCommand>> toBalanceEventStream(int accountId, BalanceEventReader balanceEventReader) {
        return eventWiring -> specifiedEventsForAccountId(accountId, balanceEventReader, eventWiring);
    }

    private static Stream<BalanceCommand> specifiedEventsForAccountId(int accountId, BalanceEventReader balanceEventReader, EventWiring eventWiring) {
        return balanceEventReader.retrieveSortedEvents(accountId, eventWiring);
    }

    private static Balance upToDateBalance(List<BalanceCommand> balanceCommands) {
        BalanceEventVisitor balanceEventVisitor = new BalanceEventVisitor(new Balance());
        balanceCommands.stream().forEach(event -> event.visit(balanceEventVisitor));
        return balanceEventVisitor.projectBalance();
    }
}
