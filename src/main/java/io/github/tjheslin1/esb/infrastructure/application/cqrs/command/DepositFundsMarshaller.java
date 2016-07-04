package io.github.tjheslin1.esb.infrastructure.application.cqrs.command;

import io.github.tjheslin1.esb.application.cqrs.command.EventJsonMarshaller;
import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsEvent;
import org.bson.Document;

public class DepositFundsMarshaller implements EventJsonMarshaller {

    @Override
    public Document marshallBalanceEvent(BalanceEvent balanceEvent) {
        DepositFundsEvent depositFundsEvent = (DepositFundsEvent) balanceEvent;

        Document depositDoc = new Document("timeOfEvent", depositFundsEvent.timeOfEvent());
        depositDoc.append("accountId", depositFundsEvent.accountId());
        depositDoc.append("amount", depositFundsEvent.amount());
        return depositDoc;
    }
}
