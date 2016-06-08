package io.github.tjheslin1.eventsourcedbanking;

import static java.lang.String.format;

public class App {

    private BankAccountRetriever bankAccountRetriever;

    public App() {
        bankAccountRetriever = new BankAccountRetriever();
    }

    private void loadTestData() {
    }

    private BankAccount accountWithId(int accountId) {
        return bankAccountRetriever.bankAccountProjectionWithId(accountId);
    }

    public static void main(String[] args) {
        App app = new App();
        app.loadTestData();

        BankAccount testAccount = app.accountWithId(7);

        System.out.println(format("Account with Id '%s' exists", testAccount.accountId()));
    }
}
