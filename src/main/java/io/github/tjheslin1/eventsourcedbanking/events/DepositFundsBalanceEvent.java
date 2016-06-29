package io.github.tjheslin1.eventsourcedbanking.events;

import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.cqrs.MongoOperations.eventDatePattern;

public class DepositFundsBalanceEvent implements BalanceEvent, Comparable {

    private final int accountId;
    private final double amount;

    private LocalDateTime timeOfEvent;

    private DepositFundsBalanceEvent(int accountId, double amount, LocalDateTime timeOfEvent) {
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

    public static DepositFundsBalanceEvent depositFundsEvent(int accountId, double amount, LocalDateTime timeOfEvent) {
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
}
