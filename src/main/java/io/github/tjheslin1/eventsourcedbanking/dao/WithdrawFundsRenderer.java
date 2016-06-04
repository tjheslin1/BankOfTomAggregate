package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.BasicDBObject;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;

public class WithdrawFundsRenderer implements JsonRenderer {

    @Override
    public BasicDBObject renderBalanceEvent(BalanceEvent balanceEvent) {
        WithdrawFundsBalanceEvent withdrawFundsEvent = (WithdrawFundsBalanceEvent) balanceEvent;

        BasicDBObject withdrawDoc = new BasicDBObject(withdrawFundsEvent.timeOfEvent(), withdrawFundsEvent.collectionName());
        withdrawDoc.append("amount", withdrawFundsEvent.amount());
        return withdrawDoc;
    }
}
