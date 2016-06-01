package io.github.tjheslin1.eventsourcedbanking;

import io.github.tjheslin1.eventsourcedbanking.events.Balance;

public class BankAccount {

    private Balance balance;

    private BankAccount(Balance balance) {
        this.balance = balance;
    }

    public Balance balance() {
        return balance;
    }

    public static BankAccount bankAccountProjection(int bankAccountId) {
        Balance balance = upToDateBalance();
        return new BankAccount(balance);
    }

    private static Balance upToDateBalance() {
        // read existing events from DB
        return new Balance();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccount that = (BankAccount) o;

        return balance.equals(that.balance);

    }

    @Override
    public int hashCode() {
        return balance.hashCode();
    }
}
