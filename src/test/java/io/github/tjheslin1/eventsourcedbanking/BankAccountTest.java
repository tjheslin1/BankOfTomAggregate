package io.github.tjheslin1.eventsourcedbanking;

import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.github.tjheslin1.eventsourcedbanking.BankAccount.bankAccountProjection;
import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;

public class BankAccountTest {

    @Test
    public void considerEventsToBankAccountToFindUpToDateBalance() {
        List<BalanceEvent> balanceEvents = Arrays.asList(depositFundsEvent(10), withdrawFundsEvent(5));

        BankAccount bankAccount = bankAccountProjection(7);
        balanceEvents.stream().forEach(event -> event.visit(bankAccount.balance()));

        System.out.println("balance=" + bankAccount.balance());
    }
}