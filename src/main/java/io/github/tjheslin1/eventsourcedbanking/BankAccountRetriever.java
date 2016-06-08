package io.github.tjheslin1.eventsourcedbanking;

import com.mongodb.MongoClient;
import io.github.tjheslin1.eventsourcedbanking.cqrs.MongoConnection;
import io.github.tjheslin1.eventsourcedbanking.cqrs.query.BalanceEventReader;
import io.github.tjheslin1.eventsourcedbanking.cqrs.query.DepositFundsUnmarshaller;
import io.github.tjheslin1.eventsourcedbanking.cqrs.query.WithdrawFundsUnmarshaller;
import io.github.tjheslin1.eventsourcedbanking.events.Balance;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;
import io.github.tjheslin1.settings.PropertiesReader;
import io.github.tjheslin1.settings.Settings;

import java.util.List;
import java.util.stream.Stream;

import static io.github.tjheslin1.eventsourcedbanking.cqrs.MongoOperations.collectionNameForEvent;
import static java.util.stream.Collectors.toList;

public class BankAccountRetriever {

    private Settings settings;
    private MongoClient mongoClient;

    public BankAccountRetriever() {
        settings = new Settings(new PropertiesReader("localhost"));
        mongoClient = new MongoConnection(settings).connection();
    }

    public BankAccount bankAccountProjectionWithId(int accountId) {
        BalanceEventReader balanceEventReader = new BalanceEventReader(mongoClient, settings);

        List<BalanceEvent> sortedBalanceEvents = sortedEvents(
                depositEventsForAccountWithId(accountId, balanceEventReader),
                withdrawalEventsForAccountWithId(accountId, balanceEventReader));

        return new BankAccount(accountId, upToDateBalance(sortedBalanceEvents));
    }

    public List<BalanceEvent> depositEventsForAccountWithId(int accountId, BalanceEventReader balanceEventReader) {
        return balanceEventReader.retrieveSorted(accountId, new DepositFundsUnmarshaller(),
                collectionNameForEvent(DepositFundsBalanceEvent.class));
    }

    public List<BalanceEvent> withdrawalEventsForAccountWithId(int accountId, BalanceEventReader balanceEventReader) {
        return balanceEventReader.retrieveSorted(accountId, new WithdrawFundsUnmarshaller(),
                collectionNameForEvent(WithdrawFundsBalanceEvent.class));
    }

    public List<BalanceEvent> sortedEvents(List<BalanceEvent> depositEvents, List<BalanceEvent> withdrawalEvents) {
        return Stream.concat(depositEvents.stream(), withdrawalEvents.stream())
                .sorted()
                .collect(toList());
    }

    private Balance upToDateBalance(List<BalanceEvent> balanceEvents) {
        Balance balance = new Balance();
        balanceEvents.stream().forEach(event -> event.visit(balance));
        return balance;
    }
}
