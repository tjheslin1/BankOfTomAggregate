package io.github.tjheslin1.eventsourcedbanking;

public class BankAccount {

    private int balance;

    private BankAccount(int balance) {

    }

    public static BankAccount bankAccountProjection(int bankAccountId) {
        int balance = upToDateBankAccount();
        return new BankAccount(balance);
    }

    private static int upToDateBankAccount() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccount that = (BankAccount) o;

        return balance == that.balance;
    }

    @Override
    public int hashCode() {
        return balance;
    }
}
