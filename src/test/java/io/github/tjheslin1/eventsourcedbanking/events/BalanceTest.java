package io.github.tjheslin1.eventsourcedbanking.events;

import org.junit.Test;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsEvent.depositFundsEvent;
import static org.assertj.core.api.Assertions.assertThat;

public class BalanceTest {

    @Test
    public void updateBalanceByDepositFundsEvent() {
        Balance balance = new Balance();
        DepositFundsEvent depositFundsEvent = depositFundsEvent(7);

        depositFundsEvent.visit(balance);

        assertThat(balance.balance()).isEqualTo(7);
    }
}