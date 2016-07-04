package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsEvent;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsEvent;

public interface EventVisitor {
    void consider(DepositFundsEvent depositFunds);
    void consider(WithdrawFundsEvent withdrawFunds);
}
