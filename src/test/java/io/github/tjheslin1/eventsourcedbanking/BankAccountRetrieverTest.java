package io.github.tjheslin1.eventsourcedbanking;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.eventsourcedbanking.cqrs.query.BalanceEventReader;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;

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

    private BankAccountRetriever bankAccountRetriever = new BankAccountRetriever();

    private BalanceEventReader balanceEventReader = mock(BalanceEventReader.class);

    @Test
    public void eventsAreRetrievedFromDatabaseAndSortedByDateTime() {
        when(balanceEventReader.retrieveSorted(ACCOUNT_ID, depositEventWiring())).thenReturn(Stream.of(
                firstDepositFundsEvent, secondDepositFundsEvent, thirdDepositFundsEvent, fourthDepositFundsEvent));

        when(balanceEventReader.retrieveSorted(ACCOUNT_ID, withdrawalEventWiring())).thenReturn(Stream.of(
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
}