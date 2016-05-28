package io.github.tjheslin1.eventsourcedbanking.events;

public class WithdrawFundsEvent implements Event {

    public final int amount;

    private WithdrawFundsEvent(int amount) {
        this.amount = amount;
    }

    public static WithdrawFundsEvent withdrawFundsEvent(int amount) {
        return new WithdrawFundsEvent(amount);
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.consider(this);
    }
}
