package io.github.tjheslin1.esb.application;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.domain.BankAccount;
import io.github.tjheslin1.esb.domain.cqrs.query.BalanceEventReader;
import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static io.github.tjheslin1.esb.application.events.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.events.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;

public class BankAccountRetrieverTest implements WithAssertions, WithMockito {

    public static final int ACCOUNT_ID = 20;

    private final Clock clock = Clock.systemDefaultZone();
    private LocalDateTime timeOfFirstEvent = LocalDateTime.now(clock);

    private DepositFundsBalanceEvent firstDepositFundsEvent = depositFundsEvent(ACCOUNT_ID, 10, timeOfFirstEvent);
    private WithdrawFundsBalanceEvent firstWithdrawalFundsEvent = withdrawFundsEvent(ACCOUNT_ID, 5, timeOfFirstEvent.plusMinutes(1));
    private DepositFundsBalanceEvent secondDepositFundsEvent = depositFundsEvent(ACCOUNT_ID, 40, timeOfFirstEvent.plusMinutes(2));
    private DepositFundsBalanceEvent thirdDepositFundsEvent = depositFundsEvent(ACCOUNT_ID, 38, timeOfFirstEvent.plusMinutes(3));
    private DepositFundsBalanceEvent fourthDepositFundsEvent = depositFundsEvent(ACCOUNT_ID, 1, timeOfFirstEvent.plusMinutes(4));
    private WithdrawFundsBalanceEvent secondWithdrawalFundsEvent = withdrawFundsEvent(ACCOUNT_ID, 16, timeOfFirstEvent.plusMinutes(5));

    private BalanceEventReader balanceEventReader = mock(BalanceEventReader.class);

    private BankAccountRetriever bankAccountRetriever = new BankAccountRetriever();

    @Test
    public void retrievesBankAccount() {
        when(balanceEventReader.retrieveSortedEvents(ACCOUNT_ID, depositEventWiring())).thenReturn(Stream.of(
                firstDepositFundsEvent));

        BankAccount bankAccount = bankAccountRetriever.bankAccountProjectionWithId(ACCOUNT_ID, balanceEventReader);

        double expectedBalance = firstDepositFundsEvent.amount();
        assertThat(bankAccount.balance().funds()).isEqualTo(expectedBalance);
    }

    @Test
    public void eventsAreRetrievedFromDatabaseAndSortedByDateTime() {
        when(balanceEventReader.retrieveSortedEvents(ACCOUNT_ID, depositEventWiring())).thenReturn(Stream.of(
                firstDepositFundsEvent, secondDepositFundsEvent, thirdDepositFundsEvent, fourthDepositFundsEvent));

        when(balanceEventReader.retrieveSortedEvents(ACCOUNT_ID, withdrawalEventWiring())).thenReturn(Stream.of(
                firstWithdrawalFundsEvent, secondWithdrawalFundsEvent
        ));

        List<BalanceEvent> sortedEvents = bankAccountRetriever.sortedEvents(ACCOUNT_ID, balanceEventReader, depositEventWiring(), withdrawalEventWiring());

        assertThat(sortedEvents).containsExactly(
                firstDepositFundsEvent,
                firstWithdrawalFundsEvent,
                secondDepositFundsEvent,
                thirdDepositFundsEvent,
                fourthDepositFundsEvent,
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