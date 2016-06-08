package io.github.tjheslin1.eventsourcedbanking.cqrs.command;

import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import org.bson.Document;

public class DepositFundsMarshaller implements BalanceEventJsonMarshaller {

    @Override
    public Document marshallBalanceEvent(BalanceEvent balanceEvent) {
        DepositFundsBalanceEvent depositFundsEvent = (DepositFundsBalanceEvent) balanceEvent;

        Document depositDoc = new Document("timeOfEvent", depositFundsEvent.timeOfEvent());
        depositDoc.append("accountId", depositFundsEvent.accountId());
        depositDoc.append("amount", depositFundsEvent.amount());
        return depositDoc;
    }
}
