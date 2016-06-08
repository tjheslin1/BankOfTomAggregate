package io.github.tjheslin1.eventsourcedbanking.events;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;

public class BalanceEventTest implements WithAssertions {

    private final Clock clock = Clock.systemDefaultZone();
    private final LocalDateTime timeOfFirstEvent = LocalDateTime.now(clock);

    private DepositFundsBalanceEvent firstDepositFundsBalanceEvent = depositFundsEvent(20, 7, timeOfFirstEvent);
    private DepositFundsBalanceEvent secondDepositFundsBalanceEvent = depositFundsEvent(20, 9, timeOfFirstEvent.plusMinutes(5));
    private WithdrawFundsBalanceEvent withdrawFundsBalanceEvent = withdrawFundsEvent(20, 4, timeOfFirstEvent.plusMinutes(10));

    @Test
    public void compareToTest() {
        assertThat(firstDepositFundsBalanceEvent.compareTo(secondDepositFundsBalanceEvent)).isEqualTo(-1);
        assertThat(firstDepositFundsBalanceEvent.compareTo(withdrawFundsBalanceEvent)).isEqualTo(-1);
        assertThat(withdrawFundsBalanceEvent.compareTo(secondDepositFundsBalanceEvent)).isEqualTo(1);
    }

    @Test(expected = IllegalStateException.class)
    public void compareToThrowsTest() {
        firstDepositFundsBalanceEvent.compareTo(firstDepositFundsBalanceEvent);
        fail("Two BalanceEvents for the same account cannot have the same 'timeOfEvent'");
    }
}