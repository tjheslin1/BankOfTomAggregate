package io.github.tjheslin1.eventsourcedbanking.events;

import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.cqrs.MongoOperations.eventDatePattern;

public class WithdrawFundsBalanceEvent implements BalanceEvent, Comparable {

    private final int accountId;
    private final int amount;

    private LocalDateTime timeOfEvent;

    private WithdrawFundsBalanceEvent(int accountId, int amount, LocalDateTime timeOfEvent) {
        this.accountId = accountId;
        this.amount = amount;
        this.timeOfEvent = timeOfEvent;
    }

    @Override
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WithdrawFundsBalanceEvent that = (WithdrawFundsBalanceEvent) o;

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
