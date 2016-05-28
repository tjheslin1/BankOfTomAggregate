package io.github.tjheslin1.eventsourcedbanking.events;

public class DepositFundsEvent implements Event {

    public final int amount;

    private DepositFundsEvent(int amount) {
        this.amount = amount;
    }

    public static DepositFundsEvent depositFundsEvent(int amount) {
        return new DepositFundsEvent(amount);
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.consider(this);
    }
}
