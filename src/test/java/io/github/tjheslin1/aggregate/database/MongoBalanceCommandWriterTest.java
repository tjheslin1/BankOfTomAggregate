package io.github.tjheslin1.aggregate.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.MongoBalanceCommandWriter;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.aggregate.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.aggregate.application.cqrs.command.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand.withdrawFundsCommand;
import static io.github.tjheslin1.aggregate.infrastructure.mongo.MongoConnection.mongoClient;
import static io.github.tjheslin1.aggregate.infrastructure.mongo.MongoOperations.collectionNameForEvent;

public class MongoBalanceCommandWriterTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 20;

    private Settings settings = mock(Settings.class);

    private MongoBalanceCommandWriter eventWriter;
    private MongoClient mongoClient;

    private final Clock clock = Clock.systemDefaultZone();

    @Before
    public void before() {
        when(settings.mongoDbPort()).thenReturn(27017);
        when(settings.mongoDbName()).thenReturn("events_store");

        mongoClient = mongoClient(settings);

        eventWriter = new MongoBalanceCommandWriter(mongoClient, settings);
    }

    @After
    public void after() {
        MongoDatabase database = mongoClient.getDatabase(settings.mongoDbName());

        MongoCollection<Document> depositFundsEventsCollection = database
                .getCollection(collectionNameForEvent(DepositFundsCommand.class));

        MongoCollection<Document> withdrawFundsEventsCollection = database
                .getCollection(collectionNameForEvent(WithdrawFundsCommand.class));

        depositFundsEventsCollection.deleteMany(new Document());
        withdrawFundsEventsCollection.deleteMany(new Document());
    }

    @Test
    public void writeDepositFundsEventToDatabaseTest() throws Exception {
        DepositFundsCommand depositFundsCommand = depositFundsCommand(ACCOUNT_ID, 6, LocalDateTime.now(clock));
        eventWriter.write(depositFundsCommand, depositEventWiring());

        assertThat(mongoClient.getDatabase(settings.mongoDbName())
                .getCollection(collectionNameForEvent(depositFundsCommand.getClass()))
                .count())
                .isEqualTo(1);
    }

    @Test
    public void writeWithdrawFundsEventToDatabaseTest() throws Exception {
        WithdrawFundsCommand withdrawFundsCommand = withdrawFundsCommand(ACCOUNT_ID, 6, LocalDateTime.now(clock));
        eventWriter.write(withdrawFundsCommand, withdrawalEventWiring());

        assertThat(mongoClient.getDatabase(settings.mongoDbName())
                .getCollection(collectionNameForEvent(withdrawFundsCommand.getClass()))
                .count())
                .isEqualTo(1);
    }

    @Test(expected = MongoWriteException.class)
    public void writesTheSameEventTwice() throws Exception {
        int depositAmount = 6;
        DepositFundsCommand depositFundsCommand = depositFundsCommand(ACCOUNT_ID, depositAmount, LocalDateTime.now(clock));

        eventWriter.write(depositFundsCommand, depositEventWiring());
        eventWriter.write(depositFundsCommand, depositEventWiring());
    }
}