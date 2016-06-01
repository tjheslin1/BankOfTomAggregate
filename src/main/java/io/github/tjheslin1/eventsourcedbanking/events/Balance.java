package io.github.tjheslin1.eventsourcedbanking.events;

public class Balance implements BalanceEventVisitor {

    private int funds;

    public Balance() {
        funds = 0;
    }

    public int balance() {
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
