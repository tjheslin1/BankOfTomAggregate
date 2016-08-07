package io.github.tjheslin1.esb.infrastructure.application.cqrs.withdraw;

import io.github.tjheslin1.esb.application.cqrs.query.EventJsonUnmarshaller;
import org.bson.Document;

import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand.withdrawFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.mongo.MongoOperations.eventDatePattern;

public class WithdrawFundsUnmarshaller implements EventJsonUnmarshaller {

    @Override
    public WithdrawFundsCommand unmarshallBalanceEvent(Document document) {
        int accountId = Integer.parseInt(document.get("accountId").toString());
        double amount = Double.parseDouble(document.get("amount").toString());

        LocalDateTime timeOfEvent = LocalDateTime.parse(document.get("timeOfEvent").toString(), eventDatePattern());

        return withdrawFundsCommand(accountId, amount, timeOfEvent);
    }
}
