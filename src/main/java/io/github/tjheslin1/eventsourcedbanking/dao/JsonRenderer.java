package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.BasicDBObject;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;

public class JsonRenderer {

    public static BasicDBObject renderDepositFundsEvent(DepositFundsBalanceEvent depositFundsEvent) {
        return new BasicDBObject(depositFundsEvent.timeOfEvent(), new Object[]{depositFundsEvent.amount()});
    }

    public static BasicDBObject renderWithdrawFundsEvent(WithdrawFundsBalanceEvent withdrawFundsEvent) {
        return new BasicDBObject(withdrawFundsEvent.timeOfEvent(), new Object[]{withdrawFundsEvent.amount()});
    }
}
