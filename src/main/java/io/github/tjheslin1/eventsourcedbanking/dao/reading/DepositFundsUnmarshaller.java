package io.github.tjheslin1.eventsourcedbanking.dao.reading;

import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import org.bson.Document;

import java.time.LocalDateTime;

import static io.github.tjheslin1.eventsourcedbanking.dao.MongoOperations.eventDatePattern;

public class DepositFundsUnmarshaller implements BalanceEventJsonUnmarshaller {

    @Override
    public DepositFundsBalanceEvent unmarshallBalanceEvent(Document document) {

        int accountId = Integer.parseInt(document.get("accountId").toString());
        int amount = Integer.parseInt(document.get("amount").toString());

        LocalDateTime timeOfEvent = LocalDateTime.parse(document.get("timeOfEvent").toString(), eventDatePattern());

        return DepositFundsBalanceEvent.depositFundsEvent(accountId, amount, timeOfEvent);
    }
}
