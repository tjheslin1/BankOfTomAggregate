package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.cqrs.deposit.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand;
import org.assertj.core.api.WithAssertions;
import org.json.JSONObject;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.cqrs.deposit.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand.withdrawFundsCommand;

public class BalanceCommandTest implements WithAssertions {

    private static final int ACCOUNT_ID = 20;
    private static final int ANOTHER_ACCOUNT_ID = 21;

    private final Clock clock = Clock.systemDefaultZone();
    private final LocalDateTime timeOfFirstEvent = LocalDateTime.now(clock);

    private DepositFundsCommand firstDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 7, timeOfFirstEvent);
    private DepositFundsCommand secondDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 9, timeOfFirstEvent.plusMinutes(5));
    private DepositFundsCommand thirdDepositFundsCommand = depositFundsCommand(ANOTHER_ACCOUNT_ID, 9, timeOfFirstEvent.plusMinutes(5));

    private WithdrawFundsCommand firstWithdrawFundsCommand = withdrawFundsCommand(ACCOUNT_ID, 4, timeOfFirstEvent.plusMinutes(10));
    private WithdrawFundsCommand secondWithdrawFundsCommand = withdrawFundsCommand(ANOTHER_ACCOUNT_ID, 4, timeOfFirstEvent);

    @Test
    public void compareToTest() {
        assertThat(firstDepositFundsCommand.compareTo(thirdDepositFundsCommand)).isEqualTo(-1);
        assertThat(firstDepositFundsCommand.compareTo(firstWithdrawFundsCommand)).isEqualTo(-1);
        assertThat(firstWithdrawFundsCommand.compareTo(thirdDepositFundsCommand)).isEqualTo(1);
    }

    @Test(expected = IllegalStateException.class)
    public void compareToThrowsTest() {
        firstDepositFundsCommand.compareTo(firstDepositFundsCommand);
        fail("Two BalanceEvents for the same account cannot have the same 'timeOfEvent'");
    }

    @Test
    public void twoEventsCanOccurAtTheSameTimeForTwoDifferentAccounts() throws Exception {
        assertThat(firstDepositFundsCommand.compareTo(secondWithdrawFundsCommand)).isEqualTo(0);
    }

    @Test
    public void toStringShouldReturnJson() throws Exception {
        JSONObject json = new JSONObject(firstDepositFundsCommand.toString());
        assertThat(json.get("accountId")).isEqualTo(firstDepositFundsCommand.accountId());
        assertThat(json.get("amount")).isEqualTo(firstDepositFundsCommand.amount());
        assertThat(json.get("timeOfEvent")).isEqualTo(firstDepositFundsCommand.timeOfEvent());
    }
}