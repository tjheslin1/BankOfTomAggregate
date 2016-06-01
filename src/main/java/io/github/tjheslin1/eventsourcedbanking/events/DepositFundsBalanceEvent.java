package io.github.tjheslin1.eventsourcedbanking.events;

import java.time.LocalDateTime;

public class DepositFundsBalanceEvent implements BalanceEvent {

    private final int amount;

    private LocalDateTime timeOfEvent;

    private DepositFundsBalanceEvent(int amount) {
        this.amount = amount;
        this.timeOfEvent = LocalDateTime.now();
    }

    public int amount() {
        return amount;
    }

    public String timeOfEvent() {
        return timeOfEvent.toString();
    }

    public static DepositFundsBalanceEvent depositFundsEvent(int amount) {
        return new DepositFundsBalanceEvent(amount);
    }

    @Override
    public void visit(BalanceEventVisitor balanceEventVisitor) {
        balanceEventVisitor.consider(this);
    }
}
