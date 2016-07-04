package io.github.tjheslin1.esb.application;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.domain.BankAccount;
import io.github.tjheslin1.esb.application.cqrs.query.BalanceEventReader;
import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static io.github.tjheslin1.esb.application.eventwiring.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.eventwiring.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand.withdrawFundsCommand;

public class BankAccountRetrieverTest implements WithAssertions, WithMockito {

    public static final int ACCOUNT_ID = 20;

    private final Clock clock = Clock.systemDefaultZone();
    private LocalDateTime timeOfFirstEvent = LocalDateTime.now(clock);

    private DepositFundsCommand firstDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 10, timeOfFirstEvent);
    private WithdrawFundsCommand firstWithdrawalFundsEvent = withdrawFundsCommand(ACCOUNT_ID, 5, timeOfFirstEvent.plusMinutes(1));
    private DepositFundsCommand secondDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 40, timeOfFirstEvent.plusMinutes(2));
    private DepositFundsCommand thirdDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 38, timeOfFirstEvent.plusMinutes(3));
    private DepositFundsCommand fourthDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 1, timeOfFirstEvent.plusMinutes(4));
    private WithdrawFundsCommand secondWithdrawalFundsEvent = withdrawFundsCommand(ACCOUNT_ID, 16, timeOfFirstEvent.plusMinutes(5));

    private BalanceEventReader balanceEventReader = mock(BalanceEventReader.class);

    private BankAccountRetriever bankAccountRetriever = new BankAccountRetriever();

    @Test
    public void retrievesBankAccount() {
        when(balanceEventReader.retrieveSortedEvents(ACCOUNT_ID, depositEventWiring())).thenReturn(Stream.of(
                firstDepositFundsCommand));

        BankAccount bankAccount = bankAccountRetriever.bankAccountProjectionWithId(ACCOUNT_ID, balanceEventReader);

        double expectedBalance = firstDepositFundsCommand.amount();
        assertThat(bankAccount.balance().funds()).isEqualTo(expectedBalance);
    }

    @Test
    public void eventsAreRetrievedFromDatabaseAndSortedByDateTime() {
        when(balanceEventReader.retrieveSortedEvents(ACCOUNT_ID, depositEventWiring())).thenReturn(Stream.of(
                firstDepositFundsCommand, secondDepositFundsCommand, thirdDepositFundsCommand, fourthDepositFundsCommand));

        when(balanceEventReader.retrieveSortedEvents(ACCOUNT_ID, withdrawalEventWiring())).thenReturn(Stream.of(
                firstWithdrawalFundsEvent, secondWithdrawalFundsEvent
        ));

        List<BalanceEvent> sortedEvents = bankAccountRetriever.sortedEvents(ACCOUNT_ID, balanceEventReader, depositEventWiring(), withdrawalEventWiring());

        assertThat(sortedEvents).containsExactly(
                firstDepositFundsCommand,
                firstWithdrawalFundsEvent,
                secondDepositFundsCommand,
                thirdDepositFundsCommand,
                fourthDepositFundsCommand,
                secondWithdrawalFundsEvent
        );
    }

    @Test
    public void retrieverHandlesEmptyResults() {
        when(balanceEventReader.retrieveSortedEvents(ACCOUNT_ID, depositEventWiring())).thenReturn(Stream.empty());

        List<BalanceEvent> sortedEvents = bankAccountRetriever.sortedEvents(ACCOUNT_ID, balanceEventReader, depositEventWiring());

        assertThat(sortedEvents.isEmpty());
    }
}