package io.github.tjheslin1.esb.infrastructure.application.cqrs.query;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.application.cqrs.query.BalanceQueryReader;
import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.tjheslin1.esb.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.esb.application.cqrs.command.WithdrawEventWiring.withdrawalEventWiring;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.DepositFundsCommand.depositFundsCommand;
import static io.github.tjheslin1.esb.infrastructure.application.cqrs.command.WithdrawFundsCommand.withdrawFundsCommand;
import static java.util.Arrays.asList;

public class MongoEventViewTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 21;

    private final Clock clock = Clock.systemDefaultZone();
    private final LocalDateTime timeOfFirstEvent = LocalDateTime.now(clock);

    private DepositFundsCommand firstDepositFundsCommand = depositFundsCommand(20, 7, timeOfFirstEvent);
    private DepositFundsCommand secondDepositFundsCommand = depositFundsCommand(20, 9, timeOfFirstEvent.plusMinutes(5));
    private WithdrawFundsCommand withdrawFundsCommand = withdrawFundsCommand(20, 4, timeOfFirstEvent.plusMinutes(10));

    private BalanceQueryReader queryReader = mock(BalanceQueryReader.class);
    private MongoEventView eventView = new MongoEventView(queryReader);

    @Test
    public void streamsEventsSortedByTime() throws Exception {
        when(queryReader.retrieveSortedEvents(ACCOUNT_ID, depositEventWiring()))
                .thenReturn(Stream.of(firstDepositFundsCommand, secondDepositFundsCommand));
        when(queryReader.retrieveSortedEvents(ACCOUNT_ID, withdrawalEventWiring()))
                .thenReturn(Stream.of(withdrawFundsCommand));

        Stream<BalanceCommand> actualEvents = eventView.eventsSortedByTime(ACCOUNT_ID,
                depositEventWiring(),
                withdrawalEventWiring());

        List<BalanceCommand> expectedEvents = asList(firstDepositFundsCommand, secondDepositFundsCommand, withdrawFundsCommand);

        assertThat(actualEvents.collect(Collectors.toList())).isEqualTo(expectedEvents);
    }
}