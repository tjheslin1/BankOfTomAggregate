package io.github.tjheslin1.esb.domain.events;

import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.eventDatePattern;
import static java.lang.String.format;

public abstract class BalanceCommand implements Event, Comparable {

    protected final int accountId;
    protected final double amount;
    protected LocalDateTime timeOfEvent;

    protected BalanceCommand(int accountId, double amount, LocalDateTime timeOfEvent) {
        this.accountId = accountId;
        this.amount = amount;
        this.timeOfEvent = timeOfEvent;
    }

    public int accountId() {
        return accountId;
    }

    public double amount() {
        return amount;
    }

    public String timeOfEvent() {
        return timeOfEvent.format(eventDatePattern());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BalanceCommand that = (BalanceCommand) o;

        if (accountId != that.accountId) return false;
        return Double.compare(that.amount, amount) == 0 && timeOfEvent.equals(that.timeOfEvent);

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
        BalanceCommand secondBalanceCommand = (BalanceCommand) o;
        LocalDateTime timeOfSecondEvent = LocalDateTime.parse(secondBalanceCommand.timeOfEvent(), eventDatePattern());
        if (timeOfEvent.isEqual(timeOfSecondEvent) && accountId == secondBalanceCommand.accountId()) {
            throw new IllegalStateException("Two BalanceEvents for the same account cannot have the same 'timeOfEvent'");
        }

        if (timeOfEvent.isEqual(timeOfSecondEvent)) {
            return 0;
        }

        return timeOfEvent.isBefore(timeOfSecondEvent) ? -1 : 1;
    }

    @Override
    public String toString() {
        return format("{\"accountId\": %s, \"amount\": %s, \"timeOfEvent\": \"%s\"}", accountId(), amount(), timeOfEvent());
    }
}
