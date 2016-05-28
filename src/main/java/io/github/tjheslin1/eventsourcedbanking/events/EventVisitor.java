package io.github.tjheslin1.eventsourcedbanking.events;

public interface EventVisitor {
    void consider(DepositFundsEvent depositFunds);
    void consider(WithdrawFundsEvent withdrawFunds);
}
