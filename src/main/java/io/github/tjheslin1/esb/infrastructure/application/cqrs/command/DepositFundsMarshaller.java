package io.github.tjheslin1.esb.infrastructure.application.cqrs.command;

import io.github.tjheslin1.esb.application.cqrs.command.EventJsonMarshaller;
import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import org.bson.Document;

public class DepositFundsMarshaller implements EventJsonMarshaller {

    @Override
    public Document marshallBalanceEvent(BalanceCommand balanceCommand) {
        DepositFundsCommand depositFundsCommand = (DepositFundsCommand) balanceCommand;

        Document depositDoc = new Document("timeOfEvent", depositFundsCommand.timeOfEvent());
        depositDoc.append("accountId", depositFundsCommand.accountId());
        depositDoc.append("amount", depositFundsCommand.amount());
        return depositDoc;
    }
}
