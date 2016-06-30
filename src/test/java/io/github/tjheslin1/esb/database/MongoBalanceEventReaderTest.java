package io.github.tjheslin1.esb.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.esb.domain.cqrs.command.BalanceEventWriter;
import io.github.tjheslin1.esb.domain.cqrs.query.BalanceEventReader;
import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent;
import io.github.tjheslin1.esb.infrastructure.domain.cqrs.command.MongoBalanceEventWriter;
import io.github.tjheslin1.esb.infrastructure.domain.cqrs.query.MongoBalanceEventReader;
import io.github.tjheslin1.esb.infrastructure.mongo.MongoConnection;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;
import io.github.tjheslin1.esb.infrastructure.settings.TestSettings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.tjheslin1.esb.application.events.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.events.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;
import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionNameForEvent;

public class MongoBalanceEventReaderTest implements WithAssertions {

    private MongoSettings mongoSettings = new TestSettings();
    private MongoConnection mongoConnection = new MongoConnection(mongoSettings);

    private BalanceEventWriter eventWriter;
    private BalanceEventReader balanceEventReader;
    private MongoClient mongoClient;

    private final Clock clock = Clock.systemDefaultZone();

    private final DepositFundsBalanceEvent firstDepositFundsBalanceEvent = depositFundsEvent(20, 6.0, LocalDateTime.now(clock));
    private final DepositFundsBalanceEvent secondDepositFundsBalanceEvent = depositFundsEvent(20, 4.0, LocalDateTime.now(clock).plusHours(1));

    private final WithdrawFundsBalanceEvent firstWithdrawFundsBalanceEvent = withdrawFundsEvent(20, 6.0, LocalDateTime.now(clock).plusMinutes(5));
    private final WithdrawFundsBalanceEvent secondWithdrawFundsBalanceEvent = withdrawFundsEvent(20, 4.0, LocalDateTime.now(clock).plusHours(1).plusMinutes(5));

    @Before
    public void before() {
        mongoClient = mongoConnection.connection();
        eventWriter = new MongoBalanceEventWriter(mongoClient, mongoSettings);
        balanceEventReader = new MongoBalanceEventReader(mongoClient, mongoSettings);

        eventWriter.write(firstDepositFundsBalanceEvent, depositEventWiring());
        eventWriter.write(secondDepositFundsBalanceEvent, depositEventWiring());

        eventWriter.write(firstWithdrawFundsBalanceEvent, withdrawalEventWiring());
        eventWriter.write(secondWithdrawFundsBalanceEvent, withdrawalEventWiring());
    }

    @After
    public void after() {
        MongoDatabase database = mongoClient.getDatabase(mongoSettings.mongoDbName());

        MongoCollection<Document> depositFundsEventsCollection = database
                .getCollection(collectionNameForEvent(DepositFundsBalanceEvent.class));

        MongoCollection<Document> withdrawFundsEventsCollection = database
                .getCollection(collectionNameForEvent(WithdrawFundsBalanceEvent.class));

        depositFundsEventsCollection.deleteMany(filterForAllDocuments());
        withdrawFundsEventsCollection.deleteMany(filterForAllDocuments());
    }

    @Test
    public void readDepositFundsEventsFromDatabaseInTimeOrder() throws Exception {
        List<BalanceEvent> actualBalanceEvents = balanceEventReader.retrieveSortedEvents(20, depositEventWiring()).collect(Collectors.toList());

        assertThat(actualBalanceEvents).containsExactly(firstDepositFundsBalanceEvent, secondDepositFundsBalanceEvent);
    }

    @Test
    public void readWithdrawalFundsEventsFromDatabaseInTimeOrder() throws Exception {
        List<BalanceEvent> actualBalanceEvents = balanceEventReader.retrieveSortedEvents(20, withdrawalEventWiring()).collect(Collectors.toList());

        assertThat(actualBalanceEvents).containsExactly(firstWithdrawFundsBalanceEvent, secondWithdrawFundsBalanceEvent);
    }

    private Document filterForAllDocuments() {
        return new Document();
    }
}