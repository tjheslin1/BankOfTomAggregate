package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsEvent;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsEvent.depositFundsEvent;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsEvent.withdrawFundsEvent;

public class BalanceEventTest implements WithAssertions {

    private final Clock clock = Clock.systemDefaultZone();
    private final LocalDateTime timeOfFirstEvent = LocalDateTime.now(clock);

    private DepositFundsEvent firstDepositFundsEvent = depositFundsEvent(20, 7, timeOfFirstEvent);
    private DepositFundsEvent secondDepositFundsEvent = depositFundsEvent(20, 9, timeOfFirstEvent.plusMinutes(5));
    private WithdrawFundsEvent withdrawFundsEvent = withdrawFundsEvent(20, 4, timeOfFirstEvent.plusMinutes(10));

    @Test
    public void compareToTest() {
        assertThat(firstDepositFundsEvent.compareTo(secondDepositFundsEvent)).isEqualTo(-1);
        assertThat(firstDepositFundsEvent.compareTo(withdrawFundsEvent)).isEqualTo(-1);
        assertThat(withdrawFundsEvent.compareTo(secondDepositFundsEvent)).isEqualTo(1);
    }

    @Test(expected = IllegalStateException.class)
    public void compareToThrowsTest() {
        firstDepositFundsEvent.compareTo(firstDepositFundsEvent);
        fail("Two BalanceEvents for the same account cannot have the same 'timeOfEvent'");
    }
}