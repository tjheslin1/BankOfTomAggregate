package io.github.tjheslin1.eventsourcedbanking.cqrs.command;

import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import org.bson.Document;

public interface BalanceEventJsonMarshaller {
    Document marshallBalanceEvent(BalanceEvent balanceEvent);
}
