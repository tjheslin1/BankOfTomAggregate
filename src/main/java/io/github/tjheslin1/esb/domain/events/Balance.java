package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand;

public class Balance implements EventVisitor {

    private double funds;

    public Balance() {
        funds = 0;
    }

    public double funds() {
        return funds;
    }

    @Override
    public void consider(DepositFundsCommand depositFunds) {
        funds += depositFunds.amount();
    }

    @Override
    public void consider(WithdrawFundsCommand withdrawFunds) {
        funds -= withdrawFunds.amount();
    }
}
