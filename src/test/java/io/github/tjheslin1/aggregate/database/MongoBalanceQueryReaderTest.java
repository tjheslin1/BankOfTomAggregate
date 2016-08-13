package io.github.tjheslin1.aggregate.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.aggregate.application.cqrs.query.BalanceQueryReader;
import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.MongoBalanceCommandWriter;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.MongoBalanceQueryReader;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.tjheslin1.aggregate.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.aggregate.application.cqrs.command.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand.withdrawFundsCommand;
import static io.github.tjheslin1.aggregate.infrastructure.mongo.MongoConnection.mongoClient;
import static io.github.tjheslin1.aggregate.infrastructure.mongo.MongoOperations.collectionNameForEvent;

public class MongoBalanceQueryReaderTest implements WithAssertions, WithMockito {

    private Settings settings = mock(Settings.class);

    private BalanceCommandWriter eventWriter;
    private BalanceQueryReader balanceQueryReader;
    private MongoClient mongoClient;

    private final Clock clock = Clock.systemDefaultZone();

    private final DepositFundsCommand firstDepositFundsCommand = depositFundsCommand(20, 6.0, LocalDateTime.now(clock));
    private final DepositFundsCommand secondDepositFundsCommand = depositFundsCommand(20, 4.0, LocalDateTime.now(clock).plusHours(1));

    private final WithdrawFundsCommand firstWithdrawFundsCommand = withdrawFundsCommand(20, 6.0, LocalDateTime.now(clock).plusMinutes(5));
    private final WithdrawFundsCommand secondWithdrawFundsCommand = withdrawFundsCommand(20, 4.0, LocalDateTime.now(clock).plusHours(1).plusMinutes(5));

    @Before
    public void before() throws Exception {
        when(settings.mongoDbPort()).thenReturn(27017);
        when(settings.mongoDbName()).thenReturn("events_store");

        mongoClient = mongoClient(settings);

        eventWriter = new MongoBalanceCommandWriter(mongoClient, settings);
        balanceQueryReader = new MongoBalanceQueryReader(mongoClient, settings);

        eventWriter.write(firstDepositFundsCommand, depositEventWiring());
        eventWriter.write(secondDepositFundsCommand, depositEventWiring());

        eventWriter.write(firstWithdrawFundsCommand, withdrawalEventWiring());
        eventWriter.write(secondWithdrawFundsCommand, withdrawalEventWiring());
    }

    @After
    public void after() {
        MongoDatabase database = mongoClient.getDatabase(settings.mongoDbName());

        MongoCollection<Document> depositFundsEventsCollection = database
                .getCollection(collectionNameForEvent(DepositFundsCommand.class));

        MongoCollection<Document> withdrawFundsEventsCollection = database
                .getCollection(collectionNameForEvent(WithdrawFundsCommand.class));

        depositFundsEventsCollection.deleteMany(filterForAllDocuments());
        withdrawFundsEventsCollection.deleteMany(filterForAllDocuments());
    }

    @Test
    public void readDepositFundsEventsFromDatabaseInTimeOrder() throws Exception {
        List<BalanceCommand> actualBalanceCommands = balanceQueryReader.retrieveSortedEvents(20, depositEventWiring()).collect(Collectors.toList());

        assertThat(actualBalanceCommands).containsExactly(firstDepositFundsCommand, secondDepositFundsCommand);
    }

    @Test
    public void readWithdrawalFundsEventsFromDatabaseInTimeOrder() throws Exception {
        List<BalanceCommand> actualBalanceCommands = balanceQueryReader.retrieveSortedEvents(20, withdrawalEventWiring()).collect(Collectors.toList());

        assertThat(actualBalanceCommands).containsExactly(firstWithdrawFundsCommand, secondWithdrawFundsCommand);
    }

    private Document filterForAllDocuments() {
        return new Document();
    }
}