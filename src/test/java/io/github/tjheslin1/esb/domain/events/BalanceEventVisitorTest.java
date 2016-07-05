package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.domain.Balance;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand.withdrawFundsCommand;

public class BalanceEventVisitorTest implements WithAssertions {

    private final Clock clock = Clock.systemDefaultZone();

    @Test
    public void updateBalanceByDepositFundsEvent() {
        BalanceEventVisitor balanceEventVisitor = new BalanceEventVisitor(new Balance());
        DepositFundsCommand depositFundsCommand = depositFundsCommand(20, 7, LocalDateTime.now(clock));

        depositFundsCommand.visit(balanceEventVisitor);
        assertThat(balanceEventVisitor.projectBalance().funds()).isEqualTo(7);
    }

    @Test
    public void updateBalanceByWithdrawFundsEvent() {
        BalanceEventVisitor balanceEventVisitor = new BalanceEventVisitor(new Balance());
        WithdrawFundsCommand depositFundsEvent = withdrawFundsCommand(20, 5, LocalDateTime.now(clock));

        depositFundsEvent.visit(balanceEventVisitor);
        assertThat(balanceEventVisitor.projectBalance().funds()).isEqualTo(-5);
    }
}