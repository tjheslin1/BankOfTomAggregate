package io.github.tjheslin1.aggregate.infrastructure.application.cqrs;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.application.cqrs.query.BalanceQueryReader;
import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.tjheslin1.aggregate.application.cqrs.command.DepositEventWiring.depositEventWiring;
import static io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit.DepositFundsCommand.depositFundsCommand;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class MongoEventViewTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 21;

    private final Clock clock = Clock.systemDefaultZone();
    private final LocalDateTime timeOfFirstEvent = LocalDateTime.now(clock);

    private DepositFundsCommand firstDepositFundsCommand = depositFundsCommand(20, 7, timeOfFirstEvent);
    private DepositFundsCommand secondDepositFundsCommand = depositFundsCommand(20, 9, timeOfFirstEvent.plusMinutes(5));

    private BalanceQueryReader queryReader = mock(BalanceQueryReader.class);

    private MongoEventView mongoEventView = new MongoEventView(queryReader);

    @Test
    public void streamsEventsSortedByTime() throws Exception {
        when(queryReader.retrieveSortedEvents(ACCOUNT_ID, depositEventWiring()))
                .thenReturn(Stream.of(firstDepositFundsCommand, secondDepositFundsCommand));

        Stream<BalanceCommand> actualEvents = mongoEventView.eventsSortedByTime(ACCOUNT_ID,
                depositEventWiring());

        List<BalanceCommand> expectedEvents = asList(firstDepositFundsCommand, secondDepositFundsCommand);

        assertThat(actualEvents.collect(toList())).isEqualTo(expectedEvents);
    }
}