package io.github.tjheslin1.esb.infrastructure.ui;

import com.mongodb.MongoClient;
import io.github.tjheslin1.esb.application.BankAccountRetriever;
import io.github.tjheslin1.esb.domain.events.Balance;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.MongoBalanceEventWriter;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.query.MongoBalanceEventReader;
import io.github.tjheslin1.esb.infrastructure.mongo.MongoConnection;
import io.github.tjheslin1.esb.infrastructure.settings.PropertiesReader;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;

import java.time.LocalDateTime;
import java.util.Scanner;

import static io.github.tjheslin1.esb.application.eventwiring.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.eventwiring.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand.withdrawFundsCommand;
import static java.lang.String.format;

public class App {

    public static void main(String[] args) {
        Settings settings = new Settings(new PropertiesReader("localhost"));

        MongoConnection mongoConnection = new MongoConnection(settings);
        MongoClient mongoClient = mongoConnection.connection();
        MongoBalanceEventWriter eventWriter = new MongoBalanceEventWriter(mongoClient, settings);
        MongoBalanceEventReader balanceEventReader = new MongoBalanceEventReader(mongoClient, settings);

        BankAccountRetriever bankAccountRetriever = new BankAccountRetriever();

        printInstructions();
        Scanner scanner = new Scanner(System.in);

        App app = new App();
        boolean running = true;
        while (running) {
            String line = scanner.nextLine();

            if (exitCommand(line)) {
                running = false;
            } else if (depositEvent(line)) {
                int accountId = accountIdFromCommand(line);
                double amount = amountFromCommand(line);
                app.deposit(eventWriter, accountId, amount);
            } else if (withdrawEvent(line)) {
                int accountId = accountIdFromCommand(line);
                double amount = amountFromCommand(line);
                app.withdraw(eventWriter, accountId, amount);
            } else if (balanceCommand(line)) {
                int accountId = accountIdFromCommand(line);
                app.balance(balanceEventReader, bankAccountRetriever, accountId);
            } else if (eventsCommand(line)) {
                int accountId = accountIdFromCommand(line);
                app.events(balanceEventReader, bankAccountRetriever, accountId);
            } else {
                printInstructions();
            }
            System.out.println("Enter a command:");
        }
    }

    public void deposit(MongoBalanceEventWriter eventWriter, int accountId, double amount) {
        eventWriter.write(depositFundsCommand(accountId, amount, LocalDateTime.now()), depositEventWiring());
        System.out.println(format("deposit event written for account: %s, for amount: %s.", accountId, amount));
    }

    public void withdraw(MongoBalanceEventWriter eventWriter, int accountId, double amount) {
        eventWriter.write(withdrawFundsCommand(accountId, amount, LocalDateTime.now()), withdrawalEventWiring());
        System.out.println(format("withdrawal event written for account: %s, for amount: %s.", accountId, amount));
    }

    public void balance(MongoBalanceEventReader balanceEventReader, BankAccountRetriever bankAccountRetriever, int accountId) {
        Balance balance = bankAccountRetriever.bankAccountProjectionWithId(accountId, balanceEventReader).balance();
        System.out.println(format("The current balance of account: %s is: %s.", accountId, balance.funds()));
    }

    public void events(MongoBalanceEventReader balanceEventReader, BankAccountRetriever bankAccountRetriever, int accountId) {
        // TODO write event to say that balance events have been requested.
        bankAccountRetriever.sortedEvents(accountId, balanceEventReader, depositEventWiring(), withdrawalEventWiring())
                .forEach(event -> System.out.println(event.getClass().getSimpleName() + " -> " + event.toString()));
        System.out.println("Finished printing events.");
    }

    public static int accountIdFromCommand(String line) {
        int startIndex = 1;
        if (line.contains(",")) {
            return Integer.parseInt(line.substring(startIndex, nextDelimiter(line, startIndex)));
        }
        return Integer.parseInt(line.substring(startIndex));
    }

    public static double amountFromCommand(String line) {
        int startIndex = line.indexOf(",") + 1;
        return Double.parseDouble(line.substring(startIndex));
    }

    private static int nextDelimiter(String line, int startIndex) {
        return line.indexOf(",", startIndex);
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
        return line.toLowerCase().equals("exit");
    }

    private static boolean depositEvent(String line) {
        return line.startsWith("d");
    }

    private static boolean withdrawEvent(String line) {
        return line.startsWith("w");
    }
}
