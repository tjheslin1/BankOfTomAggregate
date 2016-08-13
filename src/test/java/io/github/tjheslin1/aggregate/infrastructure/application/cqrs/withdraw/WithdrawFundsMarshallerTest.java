package io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WithdrawFundsMarshallerTest implements WithAssertions, WithMockito {

    private final Clock clock = Clock.systemDefaultZone();
    private final LocalDateTime timeOfEvent = LocalDateTime.now(clock);

    private WithdrawFundsCommand withdrawFundsCommand = mock(WithdrawFundsCommand.class);
    private WithdrawFundsMarshaller withdrawFundsMarshaller = new WithdrawFundsMarshaller();

    // TODO look at use of date pattern
    @Test
    public void marshallEventToMongoReadyDocument() {
        when(withdrawFundsCommand.timeOfEvent())
                .thenReturn(timeOfEvent.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS")));
        when(withdrawFundsCommand.accountId()).thenReturn(20);
        when(withdrawFundsCommand.amount()).thenReturn(28.0);

        Document expectedDocument = new Document("timeOfEvent", timeOfEvent.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS")));
        expectedDocument.append("accountId", 20);
        expectedDocument.append("amount", 28.0);

        assertThat(withdrawFundsMarshaller.marshallBalanceEvent(withdrawFundsCommand)).isEqualTo(expectedDocument);
    }
}