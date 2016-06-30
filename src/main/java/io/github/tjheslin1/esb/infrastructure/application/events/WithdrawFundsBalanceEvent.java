package io.github.tjheslin1.esb.infrastructure.application.events;

import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.domain.events.EventVisitor;

import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.eventDatePattern;

public class WithdrawFundsBalanceEvent implements BalanceEvent, Comparable {

    private final int accountId;
    private final double amount;
    private LocalDateTime timeOfEvent;

    private WithdrawFundsBalanceEvent(int accountId, double amount, LocalDateTime timeOfEvent) {
        this.accountId = accountId;
        this.amount = amount;
        this.timeOfEvent = timeOfEvent;
    }

    @Override
    public int accountId() {
        return accountId;
    }

    public double amount() {
        return amount;
    }

    public static WithdrawFundsBalanceEvent withdrawFundsEvent(int accountId, double amount, LocalDateTime timeOfEvent) {
        return new WithdrawFundsBalanceEvent(accountId, amount, timeOfEvent);
    }

    @Override
    public String timeOfEvent() {
        return timeOfEvent.format(eventDatePattern());
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.consider(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WithdrawFundsBalanceEvent that = (WithdrawFundsBalanceEvent) o;

        if (accountId != that.accountId) return false;
        if (Double.compare(that.amount, amount) != 0) return false;
        return timeOfEvent.equals(that.timeOfEvent);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = accountId;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + timeOfEvent.hashCode();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        BalanceEvent secondBalanceEvent = (BalanceEvent) o;
        LocalDateTime timeOfSecondEvent = LocalDateTime.parse(secondBalanceEvent.timeOfEvent(), eventDatePattern());
        if (timeOfEvent.isEqual(timeOfSecondEvent) && accountId == secondBalanceEvent.accountId()) {
            throw new IllegalStateException("Two BalanceEvents for the same account cannot have the same 'timeOfEvent'");
        }

        return timeOfEvent.isBefore(timeOfSecondEvent) ? -1 : 1;
    }

    @Override
    public String toString() {
        return "accountId=" + accountId +
                ", amount=" + amount +
                ", timeOfEvent=" + timeOfEvent;
    }
}
