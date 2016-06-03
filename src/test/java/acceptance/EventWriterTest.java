package acceptance;

import io.github.tjheslin1.eventsourcedbanking.dao.DepositFundsRenderer;
import io.github.tjheslin1.eventsourcedbanking.dao.EventWriter;
import io.github.tjheslin1.eventsourcedbanking.dao.MongoConnection;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.settings.Settings;
import io.github.tjheslin1.settings.TestSettings;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;

public class EventWriterTest implements WithAssertions {

    private Settings settings = new TestSettings();
    private MongoConnection mongoConnection = new MongoConnection(settings);

    private EventWriter eventWriter;

    @Before
    public void before() {
        eventWriter = new EventWriter(mongoConnection, settings);
    }

    // TODO finish once MongoConnectionTest is done
    @Ignore
    @Test
    public void writeEventToDatabaseTest() throws Exception {
        DepositFundsBalanceEvent depositFundsBalanceEvent = depositFundsEvent(6);
        eventWriter.write(depositFundsBalanceEvent, new DepositFundsRenderer());
    }
}