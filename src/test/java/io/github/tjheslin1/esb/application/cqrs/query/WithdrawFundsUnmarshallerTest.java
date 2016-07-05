package io.github.tjheslin1.esb.application.cqrs.query;

import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsUnmarshaller;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand.withdrawFundsCommand;

public class WithdrawFundsUnmarshallerTest implements WithAssertions {

    private final Clock clock = Clock.systemDefaultZone();

    private WithdrawFundsUnmarshaller withdrawFundsMarshaller = new WithdrawFundsUnmarshaller();

    @Test
    public void unmarshallEventToMongoReadyDocument() {
        WithdrawFundsCommand expectedEvent = withdrawFundsCommand(20, 8.0, LocalDateTime.now(clock));

        Document eventDoc = new Document("timeOfEvent", expectedEvent.timeOfEvent());
        eventDoc.append("accountId", expectedEvent.accountId());
        eventDoc.append("amount", expectedEvent.amount());

        WithdrawFundsCommand actualEvent = withdrawFundsMarshaller.unmarshallBalanceEvent(eventDoc);

        assertThat(actualEvent).isEqualTo(expectedEvent);
    }

}