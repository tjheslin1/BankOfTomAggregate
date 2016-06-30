package io.github.tjheslin1.esb.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.esb.infrastructure.mongo.MongoConnection;
import io.github.tjheslin1.esb.infrastructure.domain.cqrs.command.MongoBalanceEventWriter;
import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;
import io.github.tjheslin1.esb.infrastructure.settings.TestSettings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionNameForEvent;
import static io.github.tjheslin1.esb.application.events.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.esb.application.events.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;

public class MongoBalanceEventWriterTest implements WithAssertions {

    private MongoSettings mongoSettings = new TestSettings();
    private MongoConnection mongoConnection = new MongoConnection(mongoSettings);

    private MongoBalanceEventWriter eventWriter;
    private MongoClient mongoClient;

    private final Clock clock = Clock.systemDefaultZone();

    @Before
    public void before() {
        mongoClient = mongoConnection.connection();
        eventWriter = new MongoBalanceEventWriter(mongoClient, mongoSettings);
    }

    @After
    public void after() {
        MongoDatabase database = mongoClient.getDatabase(mongoSettings.mongoDbName());

        MongoCollection<Document> depositFundsEventsCollection = database
                .getCollection(collectionNameForEvent(DepositFundsBalanceEvent.class));

        MongoCollection<Document> withdrawFundsEventsCollection = database
                .getCollection(collectionNameForEvent(WithdrawFundsBalanceEvent.class));

        depositFundsEventsCollection.deleteMany(new Document());
        withdrawFundsEventsCollection.deleteMany(new Document());
    }

    @Test
    public void writeDepositFundsEventToDatabaseTest() throws Exception {
        DepositFundsBalanceEvent depositFundsBalanceEvent = depositFundsEvent(20, 6, LocalDateTime.now(clock));
        eventWriter.write(depositFundsBalanceEvent, depositEventWiring());

        assertThat(mongoClient.getDatabase(mongoSettings.mongoDbName())
                .getCollection(collectionNameForEvent(depositFundsBalanceEvent.getClass()))
                .count())
                .isEqualTo(1);
    }

    @Test
    public void writeWithdrawFundsEventToDatabaseTest() throws Exception {
        WithdrawFundsBalanceEvent withdrawFundsBalanceEvent = withdrawFundsEvent(20, 6, LocalDateTime.now(clock));
        eventWriter.write(withdrawFundsBalanceEvent, withdrawalEventWiring());

        assertThat(mongoClient.getDatabase(mongoSettings.mongoDbName())
                .getCollection(collectionNameForEvent(withdrawFundsBalanceEvent.getClass()))
                .count())
                .isEqualTo(1);
    }
}