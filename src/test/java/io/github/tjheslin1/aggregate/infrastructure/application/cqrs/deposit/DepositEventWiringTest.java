package io.github.tjheslin1.aggregate.infrastructure.application.cqrs.deposit;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import static io.github.tjheslin1.aggregate.application.cqrs.command.DepositEventWiring.clearInstance;
import static io.github.tjheslin1.aggregate.application.cqrs.command.DepositEventWiring.depositEventWiring;

public class DepositEventWiringTest implements WithAssertions, WithMockito {

    @Before
    public void before() {
        clearInstance();
    }

    @Test
    public void singletonTest() throws Exception {
        assertThat(depositEventWiring()).isEqualTo(depositEventWiring()).isEqualTo(depositEventWiring());
    }
}