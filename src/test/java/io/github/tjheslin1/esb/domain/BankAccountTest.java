package io.github.tjheslin1.esb.domain;

import io.github.tjheslin1.WithMockito;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class BankAccountTest implements WithAssertions, WithMockito {

    private final static int ACCOUNT_ID = 18;

    private final Balance balance = mock(Balance.class);

    @Test
    public void returnsFields() {
        BankAccount bankAccount = new BankAccount(ACCOUNT_ID, balance);

        assertThat(bankAccount.accountId()).isEqualTo(ACCOUNT_ID);
        assertThat(bankAccount.balance()).isEqualTo(balance);
    }
}