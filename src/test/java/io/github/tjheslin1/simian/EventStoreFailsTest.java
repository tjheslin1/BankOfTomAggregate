package io.github.tjheslin1.simian;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.MongoBalanceCommandWriter;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand;
import io.github.tjheslin1.esb.infrastructure.settings.MongoSettings;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.mongo.MongoConnection.mongoClient;
import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.collectionNameForEvent;

public class EventStoreFailsTest implements WithAssertions, WithMockito {

    public static final int ACCOUNT_ID = 20;
    private MongoSettings settings = mock(Settings.class);
    private MongoClient mongoClient;

    private MongoBalanceCommandWriter eventWriter;

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

        depositFundsEventsCollection.deleteMany(new Document("accountId", ACCOUNT_ID));
        withdrawFundsEventsCollection.deleteMany(new Document("accountId", ACCOUNT_ID));
    }

    @Test(expected = MongoWriteException.class)
    public void writesTheSameEventTwice() throws Exception {
        int depositAmount = 6;
        DepositFundsCommand depositFundsCommand = depositFundsCommand(ACCOUNT_ID, depositAmount, LocalDateTime.now(clock));

        eventWriter.write(depositFundsCommand, depositEventWiring());
        eventWriter.write(depositFundsCommand, depositEventWiring());
    }
}
