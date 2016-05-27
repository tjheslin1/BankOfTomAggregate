package io.github.tjheslin1.events;

import java.util.Arrays;
import java.util.List;

public class Balance implements EventVisitor {

    private int balance;

    public static void main(String[] args) {
        List<Event> events = Arrays.asList(new DepositFundsEvent(10), new WithdrawFundsEvent(5));

        Balance balance = new Balance();
        events.stream().forEach(event -> event.visit(balance));

        System.out.println("balance=" + balance.balance);
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
