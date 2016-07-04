package io.github.tjheslin1.esb.infrastructure.application.cqrs.command;

import io.github.tjheslin1.esb.application.cqrs.command.EventJsonMarshaller;
import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsEvent;
import org.bson.Document;

public class WithdrawFundsMarshaller implements EventJsonMarshaller {

    @Override
    public Document marshallBalanceEvent(BalanceEvent balanceEvent) {
        WithdrawFundsEvent withdrawFundsEvent = (WithdrawFundsEvent) balanceEvent;

        Document withdrawDoc = new Document("timeOfEvent", withdrawFundsEvent.timeOfEvent());
        withdrawDoc.append("accountId", withdrawFundsEvent.accountId());
        withdrawDoc.append("amount", withdrawFundsEvent.amount());
        return withdrawDoc;
    }
}
