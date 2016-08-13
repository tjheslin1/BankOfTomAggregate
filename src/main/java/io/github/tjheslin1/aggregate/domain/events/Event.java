package io.github.tjheslin1.aggregate.domain.events;

public interface Event {
    void visit(EventVisitor eventVisitor);
}
