package io.github.tjheslin1.eventsourcedbanking.dao;

import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;
import org.bson.Document;

public class WithdrawFundsMarshaller implements JsonMarshaller {

    @Override
    public Document renderBalanceEvent(BalanceEvent balanceEvent) {
        WithdrawFundsBalanceEvent withdrawFundsEvent = (WithdrawFundsBalanceEvent) balanceEvent;

        Document withdrawDoc = new Document("timeOfEvent", withdrawFundsEvent.timeOfEvent());
        withdrawDoc.append("amount", withdrawFundsEvent.amount());
        return withdrawDoc;
    }
}
