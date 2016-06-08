package io.github.tjheslin1.eventsourcedbanking.events;

import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.dao.MongoOperations.eventDatePattern;

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
        return timeOfEvent.format(eventDatePattern());
    }

    @Override
    public void visit(BalanceEventVisitor balanceEventVisitor) {
        balanceEventVisitor.consider(this);
    }
}
