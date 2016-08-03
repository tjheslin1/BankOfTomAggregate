package io.github.tjheslin1.esb.application.cqrs.command;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static io.github.tjheslin1.esb.application.cqrs.command.WithdrawEventWiring.withdrawalEventWiring;

public class WithdrawEventWiringTest implements WithAssertions, WithMockito {

    private WithdrawEventWiring wiring = withdrawalEventWiring();

    @Test
    public void singletonTest() throws Exception {
        assertThat(wiring).isEqualTo(withdrawalEventWiring()).isEqualTo(withdrawalEventWiring());
    }
}