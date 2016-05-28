package io.github.tjheslin1.eventsourcedbanking.events;

public class Balance implements EventVisitor {

    private int balance;

    public Balance() {
        balance = 0;
    }

    public int balance() {
        return balance;
    }

    @Override
    public void consider(DepositFundsEvent depositFunds) {
        balance += depositFunds.amount;
    }

    @Override
    public void consider(WithdrawFundsEvent withdrawFunds) {
        balance -= withdrawFunds.amount;
    }
}
