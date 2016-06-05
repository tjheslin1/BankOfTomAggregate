package io.github.tjheslin1.eventsourcedbanking.events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public static WithdrawFundsBalanceEvent withdrawFundsEvent(int amount) {
        return new WithdrawFundsBalanceEvent(amount);
    }

    @Override
    public String timeOfEvent() {
        return timeOfEvent.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS"));
    }

    @Override
    public void visit(BalanceEventVisitor balanceEventVisitor) {
        balanceEventVisitor.consider(this);
    }

    @Override
    public String collectionName() {
        return getClass().getSimpleName();
    }
}
