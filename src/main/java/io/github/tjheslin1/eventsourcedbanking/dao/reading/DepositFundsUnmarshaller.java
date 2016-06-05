package io.github.tjheslin1.eventsourcedbanking.dao.reading;

import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import org.bson.Document;

public class DepositFundsUnmarshaller implements BalanceEventJsonUnmarshaller {

    @Override
    public DepositFundsBalanceEvent unmarshallBalanceEvent(Document document) {
        return null;
    }
}
