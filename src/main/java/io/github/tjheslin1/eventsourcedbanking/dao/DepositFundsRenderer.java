package io.github.tjheslin1.eventsourcedbanking.dao;

import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import org.bson.Document;

public class DepositFundsRenderer implements JsonRenderer {

    @Override
    public Document renderBalanceEvent(BalanceEvent balanceEvent) {
        DepositFundsBalanceEvent depositFundsEvent = (DepositFundsBalanceEvent) balanceEvent;

        Document depositDoc = new Document(depositFundsEvent.timeOfEvent(), depositFundsEvent.collectionName());
        depositDoc.append("amount", depositFundsEvent.amount());
        return depositDoc;
    }
}
