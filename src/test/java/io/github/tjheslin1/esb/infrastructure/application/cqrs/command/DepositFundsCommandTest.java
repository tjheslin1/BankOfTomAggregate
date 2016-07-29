package io.github.tjheslin1.esb.infrastructure.application.cqrs.command;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand.depositFundsCommand;

public class DepositFundsCommandTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 35;

    private final Clock clock = Clock.systemDefaultZone();
    private LocalDateTime now = LocalDateTime.now(clock);

    @Test(expected = IllegalStateException.class)
    public void twoWithdrawFundsCommandsConflictIfTheyOccurAtTheSameTimeForTheSameAccountIOd() throws Exception {
        DepositFundsCommand firstDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 50.0, now);
        DepositFundsCommand secondDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 65.0, now);

        firstDepositFundsCommand.compareTo(secondDepositFundsCommand);
        fail("Expected Withdraw funds to throw IllegalStateException as the accountId and timeOfEvent are the same");
    }

}