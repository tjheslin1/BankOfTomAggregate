package io.github.tjheslin1.eventsourcedbanking.events;

public interface Event {
    void visit(EventVisitor eventVisitor);
}
