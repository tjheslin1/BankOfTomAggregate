package io.github.tjheslin1.eventsourcedbanking.events;

import java.time.LocalDateTime;

public class WithdrawFundsBalanceEvent implements BalanceEvent {

    private final int amount;

    private LocalDateTime timeOfEvent;

    private WithdrawFundsBalanceEvent(int amount) {
        this.amount = amount;
        this.timeOfEvent = LocalDateTime.now();
    }

    public int amount() {
        return amount;
    }

    public String timeOfEvent() {
        return timeOfEvent.toString();
    }

    public static WithdrawFundsBalanceEvent withdrawFundsEvent(int amount) {
        return new WithdrawFundsBalanceEvent(amount);
    }

    @Override
    public void visit(BalanceEventVisitor balanceEventVisitor) {
        balanceEventVisitor.consider(this);
    }
}
