package io.github.tjheslin1.esb.ui;

import com.mongodb.MongoClient;
import io.github.tjheslin1.esb.application.BankAccountRetriever;
import io.github.tjheslin1.esb.domain.BankAccount;
import io.github.tjheslin1.esb.domain.cqrs.command.BalanceEventWriter;
import io.github.tjheslin1.esb.domain.cqrs.query.BalanceEventReader;
import io.github.tjheslin1.esb.domain.events.Balance;
import io.github.tjheslin1.esb.infrastructure.domain.cqrs.command.MongoBalanceEventWriter;
import io.github.tjheslin1.esb.infrastructure.domain.cqrs.query.MongoBalanceEventReader;
import io.github.tjheslin1.esb.infrastructure.mongo.MongoConnection;
import io.github.tjheslin1.esb.infrastructure.settings.PropertiesReader;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;

import java.time.LocalDateTime;
import java.util.Scanner;

import static io.github.tjheslin1.esb.application.events.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.events.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;
import static java.lang.String.format;

public class App {

    public static final int ACCOUNT_ID = 7;

    private static MongoConnection mongoConnection;
    private static MongoClient mongoClient;
    private static BalanceEventWriter eventWriter;
    private static BalanceEventReader balanceEventReader;
    private static BankAccountRetriever bankAccountRetriever;
    private static Settings settings;

    public static void main(String[] args) {
        eventWriter = new MongoBalanceEventWriter(mongoClient, settings);
        balanceEventReader = new MongoBalanceEventReader(mongoClient, settings);

        App app = new App();
        app.loadTestData();

        BankAccount testAccount = app.accountWithId(ACCOUNT_ID);

        printInstructions();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            String line = scanner.nextLine();

            if (depositEvent(line)) {
                double amount = amountFromCommand(line);
                eventWriter.write(depositFundsEvent(ACCOUNT_ID, amount, LocalDateTime.now()), depositEventWiring());
                System.out.println(format("deposit event written for account: %s, for amount: %s.", ACCOUNT_ID, amount));
            } else if (withdrawEvent(line)) {
                double amount = amountFromCommand(line);
                eventWriter.write(withdrawFundsEvent(ACCOUNT_ID, amount, LocalDateTime.now()), withdrawalEventWiring());
                System.out.println(format("withdrawal event written for account: %s, for amount: %s.", ACCOUNT_ID, amount));
            } else if (balanceCommand(line)) {
                int accountId = (int) amountFromCommand(line);
                Balance balance = bankAccountRetriever.bankAccountProjectionWithId(accountId, balanceEventReader).balance();
                System.out.println(format("The current balance of account: %s is: %s.", accountId, balance.funds()));
            } else if (eventsCommand(line)) {
                int accountId = (int) amountFromCommand(line);
                bankAccountRetriever.sortedEvents(accountId, balanceEventReader, depositEventWiring(), withdrawalEventWiring())
                        .forEach(System.out::println);
                System.out.println("Finished print events.");
            } else if (exitCommand(line)) {
                running = false;
            } else {
                printInstructions();
            }
            System.out.println("Enter a command:");
        }

        System.out.println(format("Account with Id '%s' exists", testAccount.accountId()));
    }

    private void loadTestData() {
        settings = new Settings(new PropertiesReader("localhost"));

        mongoConnection = new MongoConnection(settings);
        mongoClient = mongoConnection.connection();

        bankAccountRetriever = new BankAccountRetriever();
    }

    private BankAccount accountWithId(int accountId) {
        return bankAccountRetriever.bankAccountProjectionWithId(accountId, balanceEventReader);
    }

    private static void printInstructions() {
        System.out.println("Enter 'd' followed immediately by a value (amount as a double) to write a deposit event. Example: d50\n" +
                "Enter 'w' followed immediately by a value (amount as a double) to write a withdrawal event. Example: w40\n" +
                "Enter 'b' followed immediately by a value (account id as an int) to view the balance. Example: b7\n" +
                "Enter 'e' followed immediately by a value (account id as an int) to view the events. Example: e7");
    }

    private static boolean eventsCommand(String line) {
        return line.startsWith("e");
    }

    private static boolean balanceCommand(String line) {
        return line.startsWith("b");
    }

    private static boolean exitCommand(String line) {
        return line.toLowerCase().toLowerCase().equals("exit");
    }

    private static boolean depositEvent(String line) {
        return line.startsWith("d");
    }

    private static boolean withdrawEvent(String line) {
        return line.startsWith("w");
    }

    private static double amountFromCommand(String line) {
        return Double.parseDouble(line.substring(1, line.length()));
    }
}
