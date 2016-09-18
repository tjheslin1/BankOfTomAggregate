package io.github.tjheslin1.aggregate.infrastructure.domain.events;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand.withdrawFundsCommand;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class BalanceEventSorterTest implements WithAssertions, WithMockito {

    public static final int ACCOUNT_ID = 20;

    private final Clock clock = Clock.systemDefaultZone();
    private LocalDateTime timeOfFirstEvent = LocalDateTime.now(clock);

    private DepositFundsCommand firstDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 10, timeOfFirstEvent);
    private WithdrawFundsCommand firstWithdrawalFundsEvent = withdrawFundsCommand(ACCOUNT_ID, 5, timeOfFirstEvent.plusMinutes(1));
    private DepositFundsCommand secondDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 40, timeOfFirstEvent.plusMinutes(2));
    private DepositFundsCommand thirdDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 38, timeOfFirstEvent.plusMinutes(3));
    private DepositFundsCommand fourthDepositFundsCommand = depositFundsCommand(ACCOUNT_ID, 1, timeOfFirstEvent.plusMinutes(4));
    private WithdrawFundsCommand secondWithdrawalFundsEvent = withdrawFundsCommand(ACCOUNT_ID, 16, timeOfFirstEvent.plusMinutes(5));

    private final BalanceEventSorter balanceEventSorter = new BalanceEventSorter();

    @Test
    public void sortsBalanceEventsByTime() throws Exception {
        List<BalanceCommand> depositCommands = asList(firstDepositFundsCommand, secondDepositFundsCommand,
                thirdDepositFundsCommand, fourthDepositFundsCommand);

        List<BalanceCommand> withdrawalCommands = asList(firstWithdrawalFundsEvent, secondWithdrawalFundsEvent);

        List<BalanceCommand> sortedEvents = balanceEventSorter.sortedEventViews(depositCommands, withdrawalCommands)
                .collect(toList());

        assertThat(sortedEvents).containsExactly(
                firstDepositFundsCommand,
                firstWithdrawalFundsEvent,
                secondDepositFundsCommand,
                thirdDepositFundsCommand,
                fourthDepositFundsCommand,
                secondWithdrawalFundsEvent
        );
    }

    @Test
    public void handleEmptyResult() throws Exception {
        List<BalanceCommand> sortedEvents = balanceEventSorter.sortedEventViews()
                .collect(toList());

        assertThat(sortedEvents).isEqualTo(emptyList());
    }
}