package io.github.tjheslin1.esb.domain.events;

public interface BalanceEvent extends Event {
    int accountId();
    String timeOfEvent();
}
