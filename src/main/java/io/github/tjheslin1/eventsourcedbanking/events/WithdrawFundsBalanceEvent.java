package io.github.tjheslin1.eventsourcedbanking.events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WithdrawFundsBalanceEvent implements BalanceEvent {

    private final int accountId;
    private final int amount;

    private LocalDateTime timeOfEvent;

    private WithdrawFundsBalanceEvent(int accountId, int amount, LocalDateTime timeOfEvent) {
        this.accountId = accountId;
        this.amount = amount;
        this.timeOfEvent = timeOfEvent;
    }

    public int accountId() {
        return accountId;
    }

    public int amount() {
        return amount;
    }

    public static WithdrawFundsBalanceEvent withdrawFundsEvent(int accountId, int amount, LocalDateTime timeOfEvent) {
        return new WithdrawFundsBalanceEvent(accountId, amount, timeOfEvent);
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
