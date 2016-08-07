package io.github.tjheslin1.esb.infrastructure.application.usecases;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.esb.application.usecases.DepositFundsUseCase;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.deposit.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.MongoBalanceCommandWriter;
import io.github.tjheslin1.esb.infrastructure.application.web.deposit.DepositRequest;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.application.cqrs.command.DepositEventWiring.depositEventWiring;

public class DepositFundsUseCaseTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 7;
    private BalanceCommandWriter commandWriter = mock(MongoBalanceCommandWriter.class);

    private Clock clock = Clock.systemDefaultZone();

    private DepositFundsUseCase depositFundsUseCase = new DepositFundsUseCase(commandWriter);

    @Test
    public void writesCommandToDatabase() throws Exception {
        LocalDateTime now = LocalDateTime.now(clock);
        depositFundsUseCase.depositFunds(new DepositRequest(ACCOUNT_ID, 50.0), now);

        verify(commandWriter).write(DepositFundsCommand.depositFundsCommand(ACCOUNT_ID, 50.0, now), depositEventWiring());
    }
}