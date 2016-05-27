package io.github.tjheslin1.events;

public interface Event {
    void visit(EventVisitor eventVisitor);
}
