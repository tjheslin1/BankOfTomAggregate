package io.github.tjheslin1.eventsourcedbanking;

import com.mongodb.MongoClient;
import io.github.tjheslin1.eventsourcedbanking.cqrs.MongoConnection;
import io.github.tjheslin1.eventsourcedbanking.cqrs.command.BalanceEventWriter;
import io.github.tjheslin1.settings.PropertiesReader;
import io.github.tjheslin1.settings.Settings;

import java.time.LocalDateTime;
import java.util.Scanner;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;
import static java.lang.String.format;

public class App {

    public static final int ACCOUNT_ID = 7;

    private static MongoConnection mongoConnection;
    private static MongoClient mongoClient;
    private static BalanceEventWriter eventWriter;
    private static BankAccountRetriever bankAccountRetriever;
    private static Settings settings;

    public App() {

    }

    private void loadTestData() {
        settings = new Settings(new PropertiesReader("localhost"));

        mongoConnection = new MongoConnection(settings);
        mongoClient = mongoConnection.connection();

        bankAccountRetriever = new BankAccountRetriever(mongoClient);
    }

    private BankAccount accountWithId(int accountId) {
        return bankAccountRetriever.bankAccountProjectionWithId(accountId);
    }

    public static void main(String[] args) {
        App app = new App();
        app.loadTestData();

        eventWriter = new BalanceEventWriter(mongoClient, settings);

        BankAccount testAccount = app.accountWithId(ACCOUNT_ID);

        printInstructions();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            String line = scanner.nextLine();
            double value = amountFromCommand(line);


            if (depositEvent(line)) {
                eventWriter.write(depositFundsEvent(ACCOUNT_ID, value, LocalDateTime.now()), depositEventWiring());
                System.out.println(format("deposit event written for account: %s, for amount: %s.", ACCOUNT_ID, value));
            } else if (withdrawEvent(line)) {
                eventWriter.write(withdrawFundsEvent(ACCOUNT_ID, value, LocalDateTime.now()), withdrawalEventWiring());
                System.out.println(format("withdrawal event written for account: %s, for amount: %s.", ACCOUNT_ID, value));
            } else if (balanceCommand(line)) {
                System.out.println(format("Balance for account %s is: %s", value, 0));
            } else if (eventsCommand(line)) {
                // specifiedEventsForAccountId
                System.out.println("NYI");
            } else if (exitCommand(line)) {
                running = false;
            } else {
                printInstructions();
            }
        }

        System.out.println(format("Account with Id '%s' exists", testAccount.accountId()));
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
