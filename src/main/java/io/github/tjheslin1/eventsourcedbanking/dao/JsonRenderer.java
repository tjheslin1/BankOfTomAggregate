package io.github.tjheslin1.eventsourcedbanking.dao;

import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import org.bson.Document;

public interface JsonRenderer {
    Document renderBalanceEvent(BalanceEvent balanceEvent);
}
