package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsEvent;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsEvent.depositFundsEvent;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsEvent.withdrawFundsEvent;

public class BalanceTest implements WithAssertions {

    private final Clock clock = Clock.systemDefaultZone();

    @Test
    public void updateBalanceByDepositFundsEvent() {
        Balance balance = new Balance();
        DepositFundsEvent depositFundsEvent = depositFundsEvent(20, 7, LocalDateTime.now(clock));

        depositFundsEvent.visit(balance);
        assertThat(balance.funds()).isEqualTo(7);
    }

    @Test
    public void updateBalanceByWithdrawFundsEvent() {
        Balance balance = new Balance();
        WithdrawFundsEvent depositFundsEvent = withdrawFundsEvent(20, 5, LocalDateTime.now(clock));

        depositFundsEvent.visit(balance);
        assertThat(balance.funds()).isEqualTo(-5);
    }
}