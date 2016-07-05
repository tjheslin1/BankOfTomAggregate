package io.github.tjheslin1.esb.application.cqrs.command;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsMarshaller;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.eventDatePattern;

public class DepositFundsMarshallerTest implements WithAssertions, WithMockito {

    private final Clock clock = Clock.systemDefaultZone();
    private final LocalDateTime timeOfEvent = LocalDateTime.now(clock);

    private DepositFundsCommand depositFundsCommand = mock(DepositFundsCommand.class);
    private DepositFundsMarshaller depositFundsMarshaller = new DepositFundsMarshaller();

    // TODO look at use of date pattern
    @Test
    public void marshallEventToMongoReadyDocument() {
        when(depositFundsCommand.timeOfEvent())
                .thenReturn(timeOfEvent.format(eventDatePattern()));
        when(depositFundsCommand.accountId()).thenReturn(20);
        when(depositFundsCommand.amount()).thenReturn(30.0);

        Document expectedDocument = new Document("timeOfEvent", timeOfEvent.format(eventDatePattern()));
        expectedDocument.append("accountId", 20);
        expectedDocument.append("amount", 30.0);

        assertThat(depositFundsMarshaller.marshallBalanceEvent(depositFundsCommand)).isEqualTo(expectedDocument);
    }
}