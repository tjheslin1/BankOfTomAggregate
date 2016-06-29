package io.github.tjheslin1.eventsourcedbanking.cqrs.query;

import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;
import org.bson.Document;

import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.cqrs.MongoOperations.eventDatePattern;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;

public class WithdrawFundsUnmarshaller implements BalanceEventJsonUnmarshaller {

    @Override
    public WithdrawFundsBalanceEvent unmarshallBalanceEvent(Document document) {
        int accountId = Integer.parseInt(document.get("accountId").toString());
        double amount = Double.parseDouble(document.get("amount").toString());

        LocalDateTime timeOfEvent = LocalDateTime.parse(document.get("timeOfEvent").toString(), eventDatePattern());

        return withdrawFundsEvent(accountId, amount, timeOfEvent);
    }
}
