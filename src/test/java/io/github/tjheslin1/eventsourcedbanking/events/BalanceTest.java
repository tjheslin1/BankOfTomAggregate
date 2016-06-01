package io.github.tjheslin1.eventsourcedbanking.events;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;

public class BalanceTest implements WithAssertions {

    @Test
    public void updateBalanceByDepositFundsEvent() {
        Balance balance = new Balance();
        DepositFundsBalanceEvent depositFundsEvent = depositFundsEvent(7);

        depositFundsEvent.visit(balance);

        assertThat(balance.balance()).isEqualTo(7);
    }
}