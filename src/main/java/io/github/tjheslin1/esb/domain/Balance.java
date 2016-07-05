package io.github.tjheslin1.esb.domain;

public class Balance {

    private double funds;

    public Balance() {
        funds = 0;
    }

    public double funds() {
        return funds;
    }

    public void applyDeposit(double value) {
        funds += value;
    }

    public void applyWithdrawal(double value) {
        funds -= value;
    }
}
