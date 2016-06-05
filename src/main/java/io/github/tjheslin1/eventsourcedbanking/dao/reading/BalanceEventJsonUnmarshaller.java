package io.github.tjheslin1.eventsourcedbanking.dao.reading;

import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import org.bson.Document;

public interface BalanceEventJsonUnmarshaller {
    BalanceEvent unmarshallBalanceEvent(Document document);
}
