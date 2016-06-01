package acceptance;

import io.github.tjheslin1.eventsourcedbanking.dao.DepositFundsRenderer;
import io.github.tjheslin1.eventsourcedbanking.dao.EventWriter;
import io.github.tjheslin1.eventsourcedbanking.dao.MongoConnection;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.settings.PropertiesReader;
import io.github.tjheslin1.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;

public class EventWriterTest implements WithAssertions {

    private MongoConnection mongoConnection = new MongoConnection();
    private Settings settings = new Settings(new PropertiesReader("localhost"));

    private EventWriter eventWriter;

    @Before
    public void before() {
        eventWriter = new EventWriter(mongoConnection, settings);
    }

    @Test
    public void writeEventToDatabaseTest() throws Exception {
        DepositFundsBalanceEvent depositFundsBalanceEvent = depositFundsEvent(6);
        eventWriter.write(depositFundsBalanceEvent, new DepositFundsRenderer());
    }
}