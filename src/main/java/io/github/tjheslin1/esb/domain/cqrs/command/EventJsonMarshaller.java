package io.github.tjheslin1.esb.domain.cqrs.command;

import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import org.bson.Document;

public interface EventJsonMarshaller {
    Document marshallBalanceEvent(BalanceEvent balanceEvent);
}
