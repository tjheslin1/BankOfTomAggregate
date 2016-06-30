package io.github.tjheslin1.esb.domain.cqrs.command;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsMarshaller;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent;
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
        when(withdrawFundsEvent.accountId()).thenReturn(20);
        when(withdrawFundsEvent.amount()).thenReturn(28.0);

        Document expectedDocument = new Document("timeOfEvent", timeOfEvent.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS")));
        expectedDocument.append("accountId", 20);
        expectedDocument.append("amount", 28.0);

        assertThat(withdrawFundsMarshaller.marshallBalanceEvent(withdrawFundsEvent)).isEqualTo(expectedDocument);
    }
}