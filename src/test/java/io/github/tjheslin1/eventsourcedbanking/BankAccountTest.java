package io.github.tjheslin1.eventsourcedbanking;

import io.github.tjheslin1.eventsourcedbanking.events.BalanceEvent;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static io.github.tjheslin1.eventsourcedbanking.BankAccount.bankAccountProjection;
import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent.depositFundsEvent;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent.withdrawFundsEvent;

public class BankAccountTest {

    private final Clock clock = Clock.systemDefaultZone();

    @Test
    public void considerEventsToBankAccountToFindUpToDateBalanceTest() {
        List<BalanceEvent> balanceEvents = Arrays.asList(
                depositFundsEvent(20, 10, LocalDateTime.now(clock)),
                withdrawFundsEvent(20, 5, LocalDateTime.now(clock))
        );

        BankAccount bankAccount = bankAccountProjection(7);
        balanceEvents.stream().forEach(event -> event.visit(bankAccount.balance()));

        System.out.println("balance=" + bankAccount.balance());
    }
}