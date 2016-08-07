package io.github.tjheslin1.esb.infrastructure.application.cqrs;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.esb.domain.events.BalanceCommand;
import io.github.tjheslin1.esb.domain.events.EventWiring;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.MongoEventStore;
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