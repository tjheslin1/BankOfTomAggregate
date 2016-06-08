package acceptance;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import io.github.tjheslin1.eventsourcedbanking.dao.MongoConnection;
import io.github.tjheslin1.eventsourcedbanking.dao.reading.BalanceEventReader;
import io.github.tjheslin1.eventsourcedbanking.dao.reading.DepositFundsUnmarshaller;
import io.github.tjheslin1.eventsourcedbanking.dao.writing.BalanceEventWriter;
import io.github.tjheslin1.eventsourcedbanking.dao.writing.DepositFundsMarshaller;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.settings.Settings;
import io.github.tjheslin1.settings.TestSettings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.dao.MongoOperations.collectionNameForEvent;
import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;

public class BalanceEventReaderTest implements WithAssertions {

    private Settings settings = new TestSettings();
    private MongoConnection mongoConnection = new MongoConnection(settings);

    private BalanceEventWriter eventWriter;
    private BalanceEventReader eventReader;
    private MongoClient mongoClient;

    private final Clock clock = Clock.systemDefaultZone();
    private final DepositFundsBalanceEvent depositFundsBalanceEvent = depositFundsEvent(20, 6, LocalDateTime.now(clock));

    @Before
    public void before() {
        mongoClient = mongoConnection.connection();
        eventWriter = new BalanceEventWriter(mongoClient, settings);
        eventReader = new BalanceEventReader(mongoClient, settings);

        eventWriter.write(depositFundsBalanceEvent, new DepositFundsMarshaller());
    }

    @After
    public void after() {
        MongoCollection<Document> depositFundsEventsCollection = mongoClient.getDatabase(settings.mongoDbName())
                .getCollection(collectionNameForEvent(DepositFundsBalanceEvent.class));

//        MongoCollection<Document> withdrawFundsEventsCollection = mongoClient.getDatabase(settings.mongoDbName())
//                .getCollection(collectionNameForEvent(WithdrawFundsBalanceEvent.class));

        depositFundsEventsCollection.deleteMany(new Document());
//        withdrawFundsEventsCollection.deleteMany(new Document());
    }

    @Ignore
    @Test
    public void readDepositFundsEventToDatabaseTest() throws Exception {
        BalanceEvent actualBalanceEvent = eventReader.read(20, LocalDateTime.now(clock),
                new DepositFundsUnmarshaller(), collectionNameForEvent(DepositFundsBalanceEvent.class));

        assertThat(actualBalanceEvent).isEqualTo(depositFundsBalanceEvent);
    }
}