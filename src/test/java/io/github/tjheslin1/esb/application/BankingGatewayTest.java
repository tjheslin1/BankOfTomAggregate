package io.github.tjheslin1.esb.application;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.domain.events.EventStore;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.LocalDateTime;

import static io.github.tjheslin1.esb.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.cqrs.command.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand.withdrawFundsCommand;

public class BankingGatewayTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 235;
    private static final double AMOUNT = 45.6;

    private EventStore eventStore = mock(EventStore.class);

    private final LocalDateTime timeOfCommand = LocalDateTime.now();

    private BankingGateway gateway = new BankingGateway(eventStore);

    @Test
    public void depositFundsStoresDepositCommandInEventStore() throws Exception {
        gateway.depositFunds(ACCOUNT_ID, AMOUNT, timeOfCommand);

        verify(eventStore).store(depositFundsCommand(ACCOUNT_ID, AMOUNT, timeOfCommand),
                depositEventWiring());
    }

    @Test
    public void withdrawFundsStoresWithdrawalCommandInEventStore() throws Exception {
        gateway.withdrawFunds(ACCOUNT_ID, AMOUNT, timeOfCommand);

        verify(eventStore).store(withdrawFundsCommand(ACCOUNT_ID, AMOUNT, timeOfCommand),
                withdrawalEventWiring());
    }
}