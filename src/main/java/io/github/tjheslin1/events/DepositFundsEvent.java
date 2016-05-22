package io.github.tjheslin1.events;

import static io.github.tjheslin1.events.EventStore.addEvent;

public class DepositFundsEvent extends Event {


    private DepositFundsEvent() {
        super();
    }

    public static void despositFunds(int bankAccountId, double amount) throws Exception {
        addEvent(new DepositFundsEvent());
    }
}
