package io.github.tjheslin1.eventsourcedbanking.cqrs.command;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.cqrs.MongoOperations.eventDatePattern;

public class DepositFundsMarshallerTest implements WithAssertions, WithMockito {

    private final Clock clock = Clock.systemDefaultZone();
    private final LocalDateTime timeOfEvent = LocalDateTime.now(clock);

    private DepositFundsBalanceEvent depositFundsEvent = mock(DepositFundsBalanceEvent.class);
    private DepositFundsMarshaller depositFundsMarshaller = new DepositFundsMarshaller();

    // TODO look at use of date pattern
    @Test
    public void marshallEventToMongoReadyDocument() {
        when(depositFundsEvent.timeOfEvent())
                .thenReturn(timeOfEvent.format(eventDatePattern()));
        when(depositFundsEvent.accountId()).thenReturn(20);
        when(depositFundsEvent.amount()).thenReturn(30);

        Document expectedDocument = new Document("timeOfEvent", timeOfEvent.format(eventDatePattern()));
        expectedDocument.append("accountId", 20);
        expectedDocument.append("amount", 30);

        assertThat(depositFundsMarshaller.marshallBalanceEvent(depositFundsEvent)).isEqualTo(expectedDocument);
    }
}