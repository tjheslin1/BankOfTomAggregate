package io.github.tjheslin1.esb.ui;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.infrastructure.ui.App;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class AppTest implements WithAssertions, WithMockito {

    @Test
    public void accountIdFromCommand() {
        String balanceLine = "b7";
        assertThat(App.accountIdFromCommand(balanceLine)).isEqualTo(7);

        String depositLine = "d7,5";
        assertThat(App.accountIdFromCommand(depositLine)).isEqualTo(7);

        String longerDepsoitLine = "b17,50.5";
        assertThat(App.accountIdFromCommand(longerDepsoitLine)).isEqualTo(17);
    }

    @Test
    public void amountFromCommandTest() {
        String line = "w7,5";
        assertThat(App.amountFromCommand(line)).isEqualTo(5);

        String longerLine = "b71,15.0";
        assertThat(App.amountFromCommand(longerLine)).isEqualTo(15.0);
    }

    @Test
    public void balanceFromCommandTest() {
        String line = "b7";
        assertThat(App.accountIdFromCommand(line)).isEqualTo(7);
    }
}