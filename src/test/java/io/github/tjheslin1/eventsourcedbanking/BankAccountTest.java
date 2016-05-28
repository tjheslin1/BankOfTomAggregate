package io.github.tjheslin1.eventsourcedbanking;

import io.github.tjheslin1.eventsourcedbanking.events.Balance;
import io.github.tjheslin1.eventsourcedbanking.events.Event;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.github.tjheslin1.eventsourcedbanking.events.DepositFundsEvent.depositFundsEvent;
import static io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsEvent.withdrawFundsEvent;

public class BankAccountTest {

    @Test
    public void test() {
        List<Event> events = Arrays.asList(depositFundsEvent(10), withdrawFundsEvent(5));

        Balance balance = new Balance();
        events.stream().forEach(event -> event.visit(balance));

        System.out.println("balance=" + balance.balance());
    }
}