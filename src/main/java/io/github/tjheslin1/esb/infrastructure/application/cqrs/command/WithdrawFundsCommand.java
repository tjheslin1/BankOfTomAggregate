package io.github.tjheslin1.esb.infrastructure.application.cqrs.command;

import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.EventVisitor;

import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.eventDatePattern;

public class WithdrawFundsCommand implements BalanceCommand, Comparable {
    // TODO referenced too much
    private final int accountId;
    private final double amount;
    private LocalDateTime timeOfEvent;

    private WithdrawFundsCommand(int accountId, double amount, LocalDateTime timeOfEvent) {
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

    public static WithdrawFundsCommand withdrawFundsCommand(int accountId, double amount, LocalDateTime timeOfEvent) {
        return new WithdrawFundsCommand(accountId, amount, timeOfEvent);
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

        WithdrawFundsCommand that = (WithdrawFundsCommand) o;

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
        BalanceCommand secondBalanceCommand = (BalanceCommand) o;
        LocalDateTime timeOfSecondEvent = LocalDateTime.parse(secondBalanceCommand.timeOfEvent(), eventDatePattern());
        if (timeOfEvent.isEqual(timeOfSecondEvent) && accountId == secondBalanceCommand.accountId()) {
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
