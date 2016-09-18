package io.github.tjheslin1.aggregate.infrastructure.application.cqrs;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.domain.banking.BankAccount;
import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.domain.events.EventSorter;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.ProjectBankAccountQuery.projectBankAccountQuery;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.withdraw.WithdrawFundsCommand.withdrawFundsCommand;
import static java.util.stream.Collectors.toList;

public class ProjectBankAccountQueryTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 20;

    private EventSorter eventSorter = mock(EventSorter.class);

    private final Clock clock = Clock.systemDefaultZone();
    private LocalDateTime timeOfEvent = LocalDateTime.now(clock);

    @Test
    public void retrievesBankAccount() throws Exception {
        DepositFundsCommand depositFundsCommand = depositFundsCommand(ACCOUNT_ID, 10, timeOfEvent);
        List<BalanceCommand> balanceCommands = Stream.of(depositFundsCommand).collect(toList());

        WithdrawFundsCommand withdrawFundsCommand = withdrawFundsCommand(ACCOUNT_ID, 7, timeOfEvent);
        List<BalanceCommand> withdrawalCommands = Stream.of(withdrawFundsCommand).collect(toList());

        when(eventSorter.sortedEventViews(balanceCommands, withdrawalCommands))
                .thenReturn(Stream.of(depositFundsCommand, withdrawFundsCommand));

        BankAccount bankAccount = projectBankAccountQuery(ACCOUNT_ID, eventSorter, balanceCommands, withdrawalCommands);

        assertThat(bankAccount.funds()).isEqualTo(3);
    }
}