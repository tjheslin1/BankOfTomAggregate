package io.github.tjheslin1.eventsourcedbanking.events;

public interface BalanceEventVisitor {
    void consider(DepositFundsBalanceEvent depositFunds);
    void consider(WithdrawFundsBalanceEvent withdrawFunds);
}
