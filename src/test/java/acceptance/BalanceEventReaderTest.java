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
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static io.github.tjheslin1.eventsourcedbanking.dao.MongoOperations.collectionNameForEvent;
import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;

public class BalanceEventReaderTest implements WithAssertions {

    private Settings settings = new TestSettings();
    private MongoConnection mongoConnection = new MongoConnection(settings);

    private BalanceEventWriter eventWriter;
    private BalanceEventReader eventReader;
    private MongoClient mongoClient;

    private final Clock clock = Clock.systemDefaultZone();
    private final DepositFundsBalanceEvent firstDepositFundsBalanceEvent = depositFundsEvent(20, 6, LocalDateTime.now(clock));
    private final DepositFundsBalanceEvent secondDepositFundsBalanceEvent = depositFundsEvent(20, 4, LocalDateTime.now(clock).plusHours(1));

    @Before
    public void before() {
        mongoClient = mongoConnection.connection();
        eventWriter = new BalanceEventWriter(mongoClient, settings);
        eventReader = new BalanceEventReader(mongoClient, settings);

        eventWriter.write(firstDepositFundsBalanceEvent, new DepositFundsMarshaller());
        eventWriter.write(secondDepositFundsBalanceEvent, new DepositFundsMarshaller());
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

    @Test
    public void readDepositFundsEventsFromDatabaseInTimeOrder() throws Exception {
        List<BalanceEvent> actualBalanceEvents = eventReader.retrieveSorted(20, new DepositFundsUnmarshaller(),
                collectionNameForEvent(DepositFundsBalanceEvent.class));

        assertThat(actualBalanceEvents).containsExactly(firstDepositFundsBalanceEvent, secondDepositFundsBalanceEvent);
    }
}