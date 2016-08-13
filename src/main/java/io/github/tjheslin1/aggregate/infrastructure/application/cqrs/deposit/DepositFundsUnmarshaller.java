package io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit;

import io.github.tjheslin1.aggregate.application.cqrs.query.EventJsonUnmarshaller;
import org.bson.Document;

import java.time.LocalDateTime;

import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.aggregate.infrastructure.mongo.MongoOperations.eventDatePattern;

public class DepositFundsUnmarshaller implements EventJsonUnmarshaller {

    @Override
    public DepositFundsCommand unmarshallBalanceEvent(Document document) {
        int accountId = Integer.parseInt(document.get("accountId").toString());
        double amount = Double.parseDouble(document.get("amount").toString());

        LocalDateTime timeOfEvent = LocalDateTime.parse(document.get("timeOfEvent").toString(), eventDatePattern());

        return depositFundsCommand(accountId, amount, timeOfEvent);
    }
}
