package io.github.tjheslin1.aggregate.application.cqrs.command;

import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import org.bson.Document;

public interface EventJsonMarshaller {
    Document marshallBalanceEvent(BalanceCommand balanceCommand);
}
