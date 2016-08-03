package io.github.tjheslin1.esb.application.cqrs.command;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static io.github.tjheslin1.esb.application.cqrs.command.DepositEventWiring.depositEventWiring;

public class DepositEventWiringTest implements WithAssertions {

    private DepositEventWiring wiring = depositEventWiring();

    @Test
    public void singletonTest() throws Exception {
        assertThat(wiring).isEqualTo(depositEventWiring()).isEqualTo(depositEventWiring());
    }
}