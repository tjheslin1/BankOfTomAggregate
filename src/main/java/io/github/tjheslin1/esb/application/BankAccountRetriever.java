package io.github.tjheslin1.esb.application;

import io.github.tjheslin1.esb.domain.BankAccount;
import io.github.tjheslin1.esb.application.cqrs.query.BalanceEventReader;
import io.github.tjheslin1.esb.domain.events.Balance;
import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.domain.events.EventWiring;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.github.tjheslin1.esb.application.eventwiring.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.eventwiring.WithdrawEventWiring.withdrawalEventWiring;
import static java.util.stream.Collectors.toList;

public class BankAccountRetriever {

    public BankAccount bankAccountProjectionWithId(int accountId, BalanceEventReader balanceEventReader) {
        List<BalanceEvent> sortedBalanceEvents = sortedEvents(accountId, balanceEventReader,
                depositEventWiring(),
                withdrawalEventWiring());

        return new BankAccount(accountId, upToDateBalance(sortedBalanceEvents));
    }

    public List<BalanceEvent> sortedEvents(int accountId, BalanceEventReader balanceEventReader, EventWiring... eventWirings) {
        return Stream.of(eventWirings).flatMap(toBalanceEventStream(accountId, balanceEventReader))
                .sorted()
                .collect(toList());
    }

    private Function<EventWiring, Stream<BalanceEvent>> toBalanceEventStream(int accountId, BalanceEventReader balanceEventReader) {
        return eventWiring -> specifiedEventsForAccountId(accountId, balanceEventReader, eventWiring);
    }

    public Stream<BalanceEvent> specifiedEventsForAccountId(int accountId, BalanceEventReader balanceEventReader, EventWiring eventWiring) {
        return balanceEventReader.retrieveSortedEvents(accountId, eventWiring);
    }

    private Balance upToDateBalance(List<BalanceEvent> balanceEvents) {
        Balance balance = new Balance();
        balanceEvents.stream().forEach(event -> event.visit(balance));
        return balance;
    }
}
