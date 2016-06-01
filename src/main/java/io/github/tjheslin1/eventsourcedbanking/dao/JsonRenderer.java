package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.BasicDBObject;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;

public class JsonRenderer {

    public static BasicDBObject renderDepositFundsEvent(DepositFundsBalanceEvent depositFundsEvent) {
        BasicDBObject depositDoc = new BasicDBObject(depositFundsEvent.timeOfEvent(), DepositFundsBalanceEvent.class.getSimpleName());
        depositDoc.append("amount", depositFundsEvent.amount());
        return depositDoc;
    }

    public static BasicDBObject renderWithdrawFundsEvent(WithdrawFundsBalanceEvent withdrawFundsEvent) {
        BasicDBObject withdrawDoc = new BasicDBObject(withdrawFundsEvent.timeOfEvent(), WithdrawFundsBalanceEvent.class.getSimpleName());
        withdrawDoc.append("amount", withdrawFundsEvent.amount());
        return withdrawDoc;
    }
}
