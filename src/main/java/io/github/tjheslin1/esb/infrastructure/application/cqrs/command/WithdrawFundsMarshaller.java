package io.github.tjheslin1.esb.infrastructure.application.cqrs.command;

import io.github.tjheslin1.esb.application.cqrs.command.EventJsonMarshaller;
import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand;
import org.bson.Document;

public class WithdrawFundsMarshaller implements EventJsonMarshaller {

    @Override
    public Document marshallBalanceEvent(BalanceEvent balanceEvent) {
        WithdrawFundsCommand withdrawFundsCommand = (WithdrawFundsCommand) balanceEvent;

        Document withdrawDoc = new Document("timeOfEvent", withdrawFundsCommand.timeOfEvent());
        withdrawDoc.append("accountId", withdrawFundsCommand.accountId());
        withdrawDoc.append("amount", withdrawFundsCommand.amount());
        return withdrawDoc;
    }
}
