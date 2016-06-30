package io.github.tjheslin1.esb.domain.events;

public interface Event {
    void visit(EventVisitor eventVisitor);
}
