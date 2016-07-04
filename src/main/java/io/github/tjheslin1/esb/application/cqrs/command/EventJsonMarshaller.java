package io.github.tjheslin1.esb.application.cqrs.command;

import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import org.bson.Document;

public interface EventJsonMarshaller {
    Document marshallBalanceEvent(BalanceEvent balanceEvent);
}