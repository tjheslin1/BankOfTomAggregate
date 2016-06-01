package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.BasicDBObject;
import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;

public interface JsonRenderer {
    BasicDBObject renderBalanceEvent(BalanceEvent balanceEvent);
}
