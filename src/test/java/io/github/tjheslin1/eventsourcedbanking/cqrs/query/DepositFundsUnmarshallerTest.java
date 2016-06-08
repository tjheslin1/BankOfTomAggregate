package io.github.tjheslin1.eventsourcedbanking.cqrs.query;

import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;

public class DepositFundsUnmarshallerTest implements WithAssertions {

    private final Clock clock = Clock.systemDefaultZone();

    private DepositFundsUnmarshaller depositFundsUnmarshaller = new DepositFundsUnmarshaller();

    @Test
    public void unmarshallEventToMongoReadyDocument() {

        DepositFundsBalanceEvent expectedEvent = depositFundsEvent(20, 8, LocalDateTime.now(clock));

        Document eventDoc = new Document("timeOfEvent", expectedEvent.timeOfEvent());
        eventDoc.append("accountId", expectedEvent.accountId());
        eventDoc.append("amount", expectedEvent.amount());

        DepositFundsBalanceEvent actualEvent = depositFundsUnmarshaller.unmarshallBalanceEvent(eventDoc);

        assertThat(actualEvent).isEqualTo(expectedEvent);
    }
}