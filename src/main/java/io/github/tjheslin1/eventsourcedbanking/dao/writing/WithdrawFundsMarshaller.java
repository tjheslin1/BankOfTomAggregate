package io.github.tjheslin1.eventsourcedbanking.dao.writing;

import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;
import org.bson.Document;

public class WithdrawFundsMarshaller implements BalanceEventJsonMarshaller {

    @Override
    public Document marshallBalanceEvent(BalanceEvent balanceEvent) {
        WithdrawFundsBalanceEvent withdrawFundsEvent = (WithdrawFundsBalanceEvent) balanceEvent;

        Document withdrawDoc = new Document("timeOfEvent", withdrawFundsEvent.timeOfEvent());
        withdrawDoc.append("accountId", withdrawFundsEvent.accountId());
        withdrawDoc.append("amount", withdrawFundsEvent.amount());
        return withdrawDoc;
    }
}
