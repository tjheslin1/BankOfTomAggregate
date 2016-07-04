package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand.withdrawFundsCommand;

public class BalanceEventTest implements WithAssertions {

    private final Clock clock = Clock.systemDefaultZone();
    private final LocalDateTime timeOfFirstEvent = LocalDateTime.now(clock);

    private DepositFundsCommand firstDepositFundsCommand = depositFundsCommand(20, 7, timeOfFirstEvent);
    private DepositFundsCommand secondDepositFundsCommand = depositFundsCommand(20, 9, timeOfFirstEvent.plusMinutes(5));
    private WithdrawFundsCommand withdrawFundsCommand = withdrawFundsCommand(20, 4, timeOfFirstEvent.plusMinutes(10));

    @Test
    public void compareToTest() {
        assertThat(firstDepositFundsCommand.compareTo(secondDepositFundsCommand)).isEqualTo(-1);
        assertThat(firstDepositFundsCommand.compareTo(withdrawFundsCommand)).isEqualTo(-1);
        assertThat(withdrawFundsCommand.compareTo(secondDepositFundsCommand)).isEqualTo(1);
    }

    @Test(expected = IllegalStateException.class)
    public void compareToThrowsTest() {
        firstDepositFundsCommand.compareTo(firstDepositFundsCommand);
        fail("Two BalanceEvents for the same account cannot have the same 'timeOfEvent'");
    }
}