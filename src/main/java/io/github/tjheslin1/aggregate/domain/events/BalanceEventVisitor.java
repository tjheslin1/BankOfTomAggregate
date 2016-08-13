package io.github.tjheslin1.aggregate.domain.events;

import io.github.tjheslin1.aggregate.domain.banking.Balance;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand;

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
