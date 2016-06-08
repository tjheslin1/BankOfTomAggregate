package io.github.tjheslin1.eventsourcedbanking;

import io.github.tjheslin1.eventsourcedbanking.events.Balance;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;

import java.util.List;

public class BankAccount {

    private final int accountId;
    private Balance balance;

    protected BankAccount(int accountId, Balance balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public int accountId() {
        return accountId;
    }

    public Balance balance() {
        return balance;
    }

    public static BankAccount bankAccountProjection(int accountId, List<BalanceEvent> balanceEvents) {
        return new BankAccount(accountId, upToDateBalance(balanceEvents));
    }

    private static Balance upToDateBalance(List<BalanceEvent> balanceEvents) {
        Balance balance = new Balance();
        balanceEvents.stream().forEach(event -> event.visit(balance));
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccount that = (BankAccount) o;

        if (accountId != that.accountId) return false;
        return balance != null ? balance.equals(that.balance) : that.balance == null;

    }

    @Override
    public int hashCode() {
        int result = accountId;
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }
}
