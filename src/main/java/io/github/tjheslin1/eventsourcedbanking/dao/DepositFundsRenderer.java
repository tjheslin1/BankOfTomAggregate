package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.BasicDBObject;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;

public class DepositFundsRenderer implements JsonRenderer {

    @Override
    public BasicDBObject renderBalanceEvent(BalanceEvent balanceEvent) {
        DepositFundsBalanceEvent depositFundsEvent = (DepositFundsBalanceEvent) balanceEvent;

        BasicDBObject depositDoc = new BasicDBObject(depositFundsEvent.timeOfEvent(), DepositFundsBalanceEvent.class.getSimpleName());
        depositDoc.append("amount", depositFundsEvent.amount());
        return depositDoc;
    }
}
