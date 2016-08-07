package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.domain.banking.Balance;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand;

public class BalanceEventVisitor implements EventVisitor {

    private Balance balance;

    public BalanceEventVisitor(Balance balance) {
        this.balance = balance;
    }

    public Balance projectBalance() {
        return balance;
    }

    @Override
    public void consider(DepositFundsCommand depositFunds) {
        balance.applyDeposit(depositFunds.amount());
    }

    @Override
    public void consider(WithdrawFundsCommand withdrawFunds) {
        balance.applyWithdrawal(withdrawFunds.amount());
    }
}
