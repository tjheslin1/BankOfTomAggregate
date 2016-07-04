package io.github.tjheslin1.esb.application.cqrs.query;

import io.github.tjheslin1.esb.infrastructure.application.cqrs.query.DepositFundsUnmarshaller;
import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsEvent;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsEvent.depositFundsEvent;

public class DepositFundsUnmarshallerTest implements WithAssertions {

    private final Clock clock = Clock.systemDefaultZone();

    private DepositFundsUnmarshaller depositFundsUnmarshaller = new DepositFundsUnmarshaller();

    @Test
    public void unmarshallEventToMongoReadyDocument() {

        DepositFundsEvent expectedEvent = depositFundsEvent(20, 8, LocalDateTime.now(clock));

        Document eventDoc = new Document("timeOfEvent", expectedEvent.timeOfEvent());
        eventDoc.append("accountId", expectedEvent.accountId());
        eventDoc.append("amount", expectedEvent.amount());

        DepositFundsEvent actualEvent = depositFundsUnmarshaller.unmarshallBalanceEvent(eventDoc);

        assertThat(actualEvent).isEqualTo(expectedEvent);
    }
}