package io.github.tjheslin1.aggregate.banking;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.domain.banking.Balance;
import io.github.tjheslin1.aggregate.domain.banking.BankAccount;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class BankAccountTest implements WithAssertions, WithMockito {

    private final static int ACCOUNT_ID = 18;

    private final Balance balance = mock(Balance.class);

    @Test
    public void returnsFields() {
        BankAccount bankAccount = new BankAccount(ACCOUNT_ID, balance);

        assertThat(bankAccount.accountId()).isEqualTo(ACCOUNT_ID);
        assertThat(bankAccount.funds()).isEqualTo(0.0);
    }
}