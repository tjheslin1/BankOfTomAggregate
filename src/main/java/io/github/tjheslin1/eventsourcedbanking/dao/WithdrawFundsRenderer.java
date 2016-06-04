package io.github.tjheslin1.eventsourcedbanking.dao;

import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;
import org.bson.Document;

public class WithdrawFundsRenderer implements JsonRenderer {

    @Override
    public Document renderBalanceEvent(BalanceEvent balanceEvent) {
        WithdrawFundsBalanceEvent withdrawFundsEvent = (WithdrawFundsBalanceEvent) balanceEvent;

        Document withdrawDoc = new Document(withdrawFundsEvent.timeOfEvent(), withdrawFundsEvent.collectionName());
        withdrawDoc.append("amount", withdrawFundsEvent.amount());
        return withdrawDoc;
    }
}
