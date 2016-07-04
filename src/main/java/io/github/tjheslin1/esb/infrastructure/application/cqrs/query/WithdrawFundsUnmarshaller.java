package io.github.tjheslin1.esb.infrastructure.application.cqrs.query;

import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsEvent;
import io.github.tjheslin1.esb.application.cqrs.query.EventJsonUnmarshaller;
import org.bson.Document;

import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.eventDatePattern;
import static io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsEvent.withdrawFundsEvent;

public class WithdrawFundsUnmarshaller implements EventJsonUnmarshaller {

    @Override
    public WithdrawFundsEvent unmarshallBalanceEvent(Document document) {
        int accountId = Integer.parseInt(document.get("accountId").toString());
        double amount = Double.parseDouble(document.get("amount").toString());

        LocalDateTime timeOfEvent = LocalDateTime.parse(document.get("timeOfEvent").toString(), eventDatePattern());

        return withdrawFundsEvent(accountId, amount, timeOfEvent);
    }
}
