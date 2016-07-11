package io.github.tjheslin1.esb.infrastructure.application.usecases;

import io.github.tjheslin1.esb.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.esb.application.usecases.UseCase;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequest;

import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand.depositFundsCommand;

public class DepositFundsUseCase implements UseCase {

    private BalanceCommandWriter commandWriter;

    public DepositFundsUseCase(BalanceCommandWriter commandWriter) {
        this.commandWriter = commandWriter;
    }

    public void depositFunds(DepositRequest depositRequest, LocalDateTime now) throws Exception {
        commandWriter.write(depositFundsCommand(depositRequest.accountId(), depositRequest.amount(), now), depositEventWiring());
    }
}
