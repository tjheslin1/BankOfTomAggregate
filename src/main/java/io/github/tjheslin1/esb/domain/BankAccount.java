package io.github.tjheslin1.esb.domain;

import io.github.tjheslin1.esb.domain.events.Balance;

public class BankAccount {

    private final int accountId;
    private Balance balance;

    public BankAccount(int accountId, Balance balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public int accountId() {
        return accountId;
    }

    public Balance balance() {
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
