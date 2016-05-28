package io.github.tjheslin1.eventsourcedbanking.events;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsEvent.depositFundsEvent;

public class BalanceTest implements WithAssertions {

    @Test
    public void updateBalanceByDepositFundsEvent() {
        Balance balance = new Balance();
        DepositFundsEvent depositFundsEvent = depositFundsEvent(7);

        depositFundsEvent.visit(balance);

        assertThat(balance.balance()).isEqualTo(7);
    }
}