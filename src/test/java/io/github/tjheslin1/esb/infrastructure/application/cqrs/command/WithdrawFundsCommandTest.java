package io.github.tjheslin1.esb.infrastructure.application.cqrs.command;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand.withdrawFundsCommand;

public class WithdrawFundsCommandTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 35;

    private final Clock clock = Clock.systemDefaultZone();
    private LocalDateTime now = LocalDateTime.now(clock);

    @Test(expected = IllegalStateException.class)
    public void twoWithdrawFundsCommandsConflictIfTheyOccurAtTheSameTimeForTheSameAccountIOd() throws Exception {
        WithdrawFundsCommand firstWithdrawFundsCommand = withdrawFundsCommand(ACCOUNT_ID, 50.0, now);
        WithdrawFundsCommand secondWithdrawFundsCommand = withdrawFundsCommand(ACCOUNT_ID, 65.0, now);

        firstWithdrawFundsCommand.compareTo(secondWithdrawFundsCommand);
        fail("Expected Withdraw funds to throw IllegalStateException as the accountId and timeOfEvent are the same");
    }
}