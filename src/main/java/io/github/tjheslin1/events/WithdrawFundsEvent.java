package io.github.tjheslin1.events;

public class WithdrawFundsEvent implements Event {

    public final int amount;

    public WithdrawFundsEvent(int amount) {
        this.amount = amount;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.consider(this);
    }
}
