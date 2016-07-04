package io.github.tjheslin1.esb.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.esb.application.cqrs.command.BalanceEventWriter;
import io.github.tjheslin1.esb.application.cqrs.query.BalanceEventReader;
import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.MongoBalanceEventWriter;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.query.MongoBalanceEventReader;
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

import static io.github.tjheslin1.esb.application.eventwiring.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.eventwiring.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand.withdrawFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionNameForEvent;

public class MongoBalanceEventReaderTest implements WithAssertions {

    private MongoSettings mongoSettings = new TestSettings();
    private MongoConnection mongoConnection = new MongoConnection(mongoSettings);

    private BalanceEventWriter eventWriter;
    private BalanceEventReader balanceEventReader;
    private MongoClient mongoClient;

    private final Clock clock = Clock.systemDefaultZone();

    private final DepositFundsCommand firstDepositFundsCommand = depositFundsCommand(20, 6.0, LocalDateTime.now(clock));
    private final DepositFundsCommand secondDepositFundsCommand = depositFundsCommand(20, 4.0, LocalDateTime.now(clock).plusHours(1));

    private final WithdrawFundsCommand firstWithdrawFundsCommand = withdrawFundsCommand(20, 6.0, LocalDateTime.now(clock).plusMinutes(5));
    private final WithdrawFundsCommand secondWithdrawFundsCommand = withdrawFundsCommand(20, 4.0, LocalDateTime.now(clock).plusHours(1).plusMinutes(5));

    @Before
    public void before() {
        mongoClient = mongoConnection.connection();
        eventWriter = new MongoBalanceEventWriter(mongoClient, mongoSettings);
        balanceEventReader = new MongoBalanceEventReader(mongoClient, mongoSettings);

        eventWriter.write(firstDepositFundsCommand, depositEventWiring());
        eventWriter.write(secondDepositFundsCommand, depositEventWiring());

        eventWriter.write(firstWithdrawFundsCommand, withdrawalEventWiring());
        eventWriter.write(secondWithdrawFundsCommand, withdrawalEventWiring());
    }

    @After
    public void after() {
        MongoDatabase database = mongoClient.getDatabase(mongoSettings.mongoDbName());

        MongoCollection<Document> depositFundsEventsCollection = database
                .getCollection(collectionNameForEvent(DepositFundsCommand.class));

        MongoCollection<Document> withdrawFundsEventsCollection = database
                .getCollection(collectionNameForEvent(WithdrawFundsCommand.class));

        depositFundsEventsCollection.deleteMany(filterForAllDocuments());
        withdrawFundsEventsCollection.deleteMany(filterForAllDocuments());
    }

    @Test
    public void readDepositFundsEventsFromDatabaseInTimeOrder() throws Exception {
        List<BalanceEvent> actualBalanceEvents = balanceEventReader.retrieveSortedEvents(20, depositEventWiring()).collect(Collectors.toList());

        assertThat(actualBalanceEvents).containsExactly(firstDepositFundsCommand, secondDepositFundsCommand);
    }

    @Test
    public void readWithdrawalFundsEventsFromDatabaseInTimeOrder() throws Exception {
        List<BalanceEvent> actualBalanceEvents = balanceEventReader.retrieveSortedEvents(20, withdrawalEventWiring()).collect(Collectors.toList());

        assertThat(actualBalanceEvents).containsExactly(firstWithdrawFundsCommand, secondWithdrawFundsCommand);
    }

    private Document filterForAllDocuments() {
        return new Document();
    }
}