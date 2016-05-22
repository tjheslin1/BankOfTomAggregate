package io.github.tjheslin1;

import io.github.tjheslin1.banking.BankAccount;
import org.junit.Test;

import static io.github.tjheslin1.banking.BankAccount.bankAccountProjection;
import static org.junit.Assert.assertEquals;

public class BankAppTest {

    @Test
    public void shouldLogInToUpdateToBankAccountTest() {
        BankAccount expected = bankAccountProjection(7);

        BankAccount actual = bankAccountProjection(7);
        assertEquals(actual, expected);
    }
}