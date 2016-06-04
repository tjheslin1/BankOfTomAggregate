package io.github.tjheslin1.eventsourcedbanking.events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        return timeOfEvent.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    public static DepositFundsBalanceEvent depositFundsEvent(int amount) {
        return new DepositFundsBalanceEvent(amount);
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
