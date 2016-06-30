package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsBalanceEvent;

public interface EventVisitor {
    void consider(DepositFundsBalanceEvent depositFunds);
    void consider(WithdrawFundsBalanceEvent withdrawFunds);
}
