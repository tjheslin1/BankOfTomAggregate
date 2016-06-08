package io.github.tjheslin1.eventsourcedbanking.events;

public interface BalanceEvent {
    void visit(BalanceEventVisitor balanceEventVisitor);
    String timeOfEvent();
}
