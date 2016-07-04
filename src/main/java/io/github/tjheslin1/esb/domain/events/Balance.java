package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsEvent;

public class Balance implements EventVisitor {

    private double funds;

    public Balance() {
        funds = 0;
    }

    public double funds() {
        return funds;
    }

    @Override
    public void consider(DepositFundsEvent depositFunds) {
        funds += depositFunds.amount();
    }

    @Override
    public void consider(WithdrawFundsEvent withdrawFunds) {
        funds -= withdrawFunds.amount();
    }
}
