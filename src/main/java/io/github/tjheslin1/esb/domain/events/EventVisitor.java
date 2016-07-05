package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand;

public interface EventVisitor {
    void consider(DepositFundsCommand depositFunds);
    void consider(WithdrawFundsCommand withdrawFunds);
}
