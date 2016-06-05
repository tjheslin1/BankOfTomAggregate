package acceptance;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import io.github.tjheslin1.eventsourcedbanking.dao.DepositFundsRenderer;
import io.github.tjheslin1.eventsourcedbanking.dao.EventWriter;
import io.github.tjheslin1.eventsourcedbanking.dao.MongoConnection;
import io.github.tjheslin1.eventsourcedbanking.dao.WithdrawFundsRenderer;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;
import io.github.tjheslin1.settings.Settings;
import io.github.tjheslin1.settings.TestSettings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;

public class EventWriterTest implements WithAssertions {

    private Settings settings = new TestSettings();
    private MongoConnection mongoConnection = new MongoConnection(settings);

    private EventWriter eventWriter;
    private MongoClient mongoClient;

    @Before
    public void before() {
        mongoClient = mongoConnection.connection();
        eventWriter = new EventWriter(mongoClient, settings);
    }

    @After
    public void after() {
        MongoCollection<Document> depositFundsEventsCollection = mongoClient.getDatabase(settings.mongoDbName())
                .getCollection(DepositFundsBalanceEvent.class.getSimpleName());

        MongoCollection<Document> withdrawFundsEventsCollection = mongoClient.getDatabase(settings.mongoDbName())
                .getCollection(WithdrawFundsBalanceEvent.class.getSimpleName());

        depositFundsEventsCollection.deleteMany(new Document());
        withdrawFundsEventsCollection.deleteMany(new Document());
    }

    @Test
    public void writeDepositFundsEventToDatabaseTest() throws Exception {
        DepositFundsBalanceEvent depositFundsBalanceEvent = depositFundsEvent(6);
        eventWriter.write(depositFundsBalanceEvent, new DepositFundsRenderer());

        assertThat(mongoClient.getDatabase(settings.mongoDbName())
                .getCollection(depositFundsBalanceEvent.collectionName())
                .count())
                .isEqualTo(1);
    }

    @Test
    public void writeWithdrawFundsEventToDatabaseTest() throws Exception {
        WithdrawFundsBalanceEvent withdrawFundsBalanceEvent = withdrawFundsEvent(6);
        eventWriter.write(withdrawFundsBalanceEvent, new WithdrawFundsRenderer());

        assertThat(mongoClient.getDatabase(settings.mongoDbName())
                .getCollection(withdrawFundsBalanceEvent.collectionName())
                .count())
                .isEqualTo(1);
    }
}