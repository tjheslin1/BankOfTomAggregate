package io.github.tjheslin1.eventsourcedbanking.events;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;

public class BalanceTest implements WithAssertions {

    private final Clock clock = Clock.systemDefaultZone();

    @Test
    public void updateBalanceByDepositFundsEvent() {
        Balance balance = new Balance();
        DepositFundsBalanceEvent depositFundsEvent = depositFundsEvent(20, 7, LocalDateTime.now(clock));

        depositFundsEvent.visit(balance);
        assertThat(balance.funds()).isEqualTo(7);
    }

    @Test
    public void updateBalanceByWithdrawFundsEvent() {
        Balance balance = new Balance();
        WithdrawFundsBalanceEvent depositFundsEvent = withdrawFundsEvent(20, 5, LocalDateTime.now(clock));

        depositFundsEvent.visit(balance);
        assertThat(balance.funds()).isEqualTo(-5);
    }
}