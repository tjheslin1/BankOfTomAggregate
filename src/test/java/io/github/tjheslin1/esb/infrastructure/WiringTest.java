package io.github.tjheslin1.esb.infrastructure;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.application.usecases.DepositFundsUseCase;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class WiringTest implements WithAssertions, WithMockito {

    private Settings settings = mock(Settings.class);
    private Wiring wiring = new Wiring(settings);

    @Test
    public void constructsDepositUseCase() {
        assertThat(wiring.depositFundsUseCase()).isInstanceOf(DepositFundsUseCase.class);
    }
}