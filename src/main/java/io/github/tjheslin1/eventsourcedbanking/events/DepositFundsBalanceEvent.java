package io.github.tjheslin1.eventsourcedbanking.events;

import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.dao.MongoOperations.eventDatePattern;

public class DepositFundsBalanceEvent implements BalanceEvent {

    private final int accountId;
    private final int amount;

    private LocalDateTime timeOfEvent;

    private DepositFundsBalanceEvent(int accountId, int amount, LocalDateTime timeOfEvent) {
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

    public static DepositFundsBalanceEvent depositFundsEvent(int accountId, int amount, LocalDateTime timeOfEvent) {
        return new DepositFundsBalanceEvent(accountId, amount, timeOfEvent);
    }

    @Override
    public String timeOfEvent() {
        return timeOfEvent.format(eventDatePattern());
    }

    @Override
    public void visit(BalanceEventVisitor balanceEventVisitor) {
        balanceEventVisitor.consider(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepositFundsBalanceEvent that = (DepositFundsBalanceEvent) o;

        if (accountId != that.accountId) return false;
        if (amount != that.amount) return false;
        return timeOfEvent.equals(that.timeOfEvent);

    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + amount;
        result = 31 * result + timeOfEvent.hashCode();
        return result;
    }
}
