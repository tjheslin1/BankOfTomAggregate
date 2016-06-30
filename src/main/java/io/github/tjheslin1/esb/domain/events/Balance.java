package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent;

public class Balance implements EventVisitor {

    private double funds;

    public Balance() {
        funds = 0;
    }

    public double funds() {
        return funds;
    }

    @Override
    public void consider(DepositFundsBalanceEvent depositFunds) {
        funds += depositFunds.amount();
    }

    @Override
    public void consider(WithdrawFundsBalanceEvent withdrawFunds) {
        funds -= withdrawFunds.amount();
    }
}
