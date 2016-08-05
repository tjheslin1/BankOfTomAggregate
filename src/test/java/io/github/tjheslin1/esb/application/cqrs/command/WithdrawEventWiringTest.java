package io.github.tjheslin1.esb.application.cqrs.command;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import static io.github.tjheslin1.esb.application.cqrs.command.WithdrawEventWiring.clearInstance;
import static io.github.tjheslin1.esb.application.cqrs.command.WithdrawEventWiring.withdrawalEventWiring;

public class WithdrawEventWiringTest implements WithAssertions, WithMockito {

    @Before
    public void before() {
        clearInstance();
    }

    @Test
    public void singletonTest() throws Exception {
        assertThat(withdrawalEventWiring()).isEqualTo(withdrawalEventWiring()).isEqualTo(withdrawalEventWiring());
    }
}