package io.github.tjheslin1.aggregate.application.usecases;

import io.github.tjheslin1.aggregate.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.aggregate.infrastructure.application.web.deposit.DepositRequest;

import java.time.LocalDateTime;

import static io.github.tjheslin1.aggregate.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand.depositFundsCommand;

public class DepositFundsUseCase {

    private BalanceCommandWriter commandWriter;

    public DepositFundsUseCase(BalanceCommandWriter commandWriter) {
        this.commandWriter = commandWriter;
    }

    public void depositFunds(DepositRequest depositRequest, LocalDateTime now) throws Exception {
        commandWriter.write(depositFundsCommand(depositRequest.accountId(), depositRequest.amount(), now), depositEventWiring());
    }
}
