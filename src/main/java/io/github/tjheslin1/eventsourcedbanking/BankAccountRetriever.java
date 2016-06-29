package io.github.tjheslin1.eventsourcedbanking;

import com.mongodb.MongoClient;
import io.github.tjheslin1.eventsourcedbanking.cqrs.MongoConnection;
import io.github.tjheslin1.eventsourcedbanking.cqrs.query.BalanceEventReader;
import io.github.tjheslin1.eventsourcedbanking.events.Balance;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.EventWiring;
import io.github.tjheslin1.settings.MongoSettings;
import io.github.tjheslin1.settings.PropertiesReader;
import io.github.tjheslin1.settings.Settings;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class BankAccountRetriever {

    private MongoSettings mongoSettings;
    private MongoClient mongoClient;

    public BankAccountRetriever() {
        this.mongoSettings = new Settings(new PropertiesReader("localhost"));
        this.mongoClient = new MongoConnection(mongoSettings).connection();
    }

    public BankAccountRetriever(MongoClient mongoClient) {
        mongoSettings = new Settings(new PropertiesReader("localhost"));
        this.mongoClient = mongoClient;
    }

    public BankAccount bankAccountProjectionWithId(int accountId) {
        BalanceEventReader balanceEventReader = new BalanceEventReader(mongoClient, mongoSettings);
        List<BalanceEvent> sortedBalanceEvents = sortedEvents(accountId, balanceEventReader);

        return new BankAccount(accountId, upToDateBalance(sortedBalanceEvents));
    }

    public List<BalanceEvent> sortedEvents(int accountId, BalanceEventReader balanceEventReader, EventWiring... eventWirings) {

        Stream<Stream<BalanceEvent>> test = Stream.of(eventWirings).map(toBalanceEventStream(accountId, balanceEventReader));
        return test
                .reduce(Stream::concat).get()
                .sorted()
                .collect(toList());
    }

    private Function<EventWiring, Stream<BalanceEvent>> toBalanceEventStream(int accountId, BalanceEventReader balanceEventReader) {
        return eventWiring -> specifiedEventsForAccountId(accountId, balanceEventReader, eventWiring);
    }

    public Stream<BalanceEvent> specifiedEventsForAccountId(int accountId, BalanceEventReader balanceEventReader, EventWiring eventWiring) {
        return balanceEventReader.retrieveSorted(accountId, eventWiring);
    }

    private Balance upToDateBalance(List<BalanceEvent> balanceEvents) {
        Balance balance = new Balance();
        balanceEvents.stream().forEach(event -> event.visit(balance));
        return balance;
    }
}
