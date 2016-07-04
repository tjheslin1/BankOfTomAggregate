package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.events.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.events.WithdrawFundsCommand;

public interface EventVisitor {
    void consider(DepositFundsCommand depositFunds);
    void consider(WithdrawFundsCommand withdrawFunds);
}
