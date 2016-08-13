package io.github.tjheslin1.aggregate.application.cqrs.query;

import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import org.bson.Document;

public interface EventJsonUnmarshaller {
    BalanceCommand unmarshallBalanceEvent(Document document);
}
