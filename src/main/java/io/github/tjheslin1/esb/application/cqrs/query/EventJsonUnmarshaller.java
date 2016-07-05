package io.github.tjheslin1.esb.application.cqrs.query;

import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import org.bson.Document;

public interface EventJsonUnmarshaller {
    BalanceCommand unmarshallBalanceEvent(Document document);
}
