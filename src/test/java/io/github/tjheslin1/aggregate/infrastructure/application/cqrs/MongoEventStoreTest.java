package io.github.tjheslin1.aggregate.infrastructure.application.cqrs;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.aggregate.domain.events.BalanceCommand;
import io.github.tjheslin1.aggregate.domain.events.EventWiring;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class MongoEventStoreTest implements WithAssertions, WithMockito {

    private final BalanceCommandWriter commandWriter = mock(BalanceCommandWriter.class);
    private final BalanceCommand balanceCommand = mock(BalanceCommand.class);
    private final EventWiring eventWiring = mock(EventWiring.class);

    private MongoEventStore eventStore = new MongoEventStore(commandWriter);

    @Test
    public void shouldStoreCommand() throws Exception {
        eventStore.store(balanceCommand, eventWiring);

        verify(commandWriter).write(balanceCommand, eventWiring);
    }
}