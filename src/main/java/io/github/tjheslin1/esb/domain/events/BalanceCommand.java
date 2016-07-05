package io.github.tjheslin1.esb.domain.events;

public interface BalanceCommand extends Event {
    int accountId();
    String timeOfEvent();
}
