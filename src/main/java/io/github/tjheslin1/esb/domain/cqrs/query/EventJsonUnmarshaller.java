package io.github.tjheslin1.esb.domain.cqrs.query;

import io.github.tjheslin1.esb.domain.events.BalanceEvent;
import org.bson.Document;

public interface EventJsonUnmarshaller {
    BalanceEvent unmarshallBalanceEvent(Document document);
}
