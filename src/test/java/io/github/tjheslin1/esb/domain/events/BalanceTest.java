package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand.withdrawFundsCommand;

public class BalanceTest implements WithAssertions {

    private final Clock clock = Clock.systemDefaultZone();

    @Test
    public void updateBalanceByDepositFundsEvent() {
        Balance balance = new Balance();
        DepositFundsCommand depositFundsCommand = depositFundsCommand(20, 7, LocalDateTime.now(clock));

        depositFundsCommand.visit(balance);
        assertThat(balance.funds()).isEqualTo(7);
    }

    @Test
    public void updateBalanceByWithdrawFundsEvent() {
        Balance balance = new Balance();
        WithdrawFundsCommand depositFundsEvent = withdrawFundsCommand(20, 5, LocalDateTime.now(clock));

        depositFundsEvent.visit(balance);
        assertThat(balance.funds()).isEqualTo(-5);
    }
}