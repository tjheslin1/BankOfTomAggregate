package io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit;

import io.github.tjheslin1.aggregate.application.cqrs.command.EventJsonMarshaller;
import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import org.bson.Document;

import java.util.UUID;

public class DepositFundsMarshaller implements EventJsonMarshaller {

    @Override
    public Document marshallBalanceEvent(BalanceCommand balanceCommand) {
        DepositFundsCommand depositFundsCommand = (DepositFundsCommand) balanceCommand;

        Document depositDoc = new Document("_id", uuidFromSeed(depositFundsCommand));
        depositDoc.append("timeOfEvent", depositFundsCommand.timeOfEvent());
        depositDoc.append("accountId", depositFundsCommand.accountId());
        depositDoc.append("amount", depositFundsCommand.amount());
        return depositDoc;
    }

    private UUID uuidFromSeed(DepositFundsCommand balanceCommand) {
        String seed = balanceCommand.timeOfEvent() + balanceCommand.accountId() + balanceCommand.amount();
        return UUID.nameUUIDFromBytes(seed.getBytes());
    }
}
