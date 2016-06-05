package io.github.tjheslin1.eventsourcedbanking.dao.writing;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WithdrawFundsMarshallerTest implements WithAssertions, WithMockito {

    private final Clock clock = Clock.systemDefaultZone();
    private final LocalDateTime timeOfEvent = LocalDateTime.now(clock);

    private WithdrawFundsBalanceEvent withdrawFundsEvent = mock(WithdrawFundsBalanceEvent.class);
    private WithdrawFundsMarshaller withdrawFundsMarshaller = new WithdrawFundsMarshaller();

    // TODO look at use of date pattern
    @Test
    public void marshallEventToMongoReadyDocument() {
        when(withdrawFundsEvent.timeOfEvent())
                .thenReturn(timeOfEvent.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS")));
        when(withdrawFundsEvent.amount()).thenReturn(28);

        Document expectedDocument = new Document("timeOfEvent", timeOfEvent.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS")));
        expectedDocument.append("amount", 28);

        assertThat(withdrawFundsMarshaller.marshallBalanceEvent(withdrawFundsEvent)).isEqualTo(expectedDocument);
    }
}