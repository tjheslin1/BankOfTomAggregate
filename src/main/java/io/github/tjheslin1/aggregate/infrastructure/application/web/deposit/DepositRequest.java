package io.github.tjheslin1.aggregate.infrastructure.application.web.deposit;

import io.github.tjheslin1.aggregate.application.web.Request;

import static java.lang.String.format;

public class DepositRequest implements Request {

    private int accountId;
    private double amount;

    public DepositRequest(int accountId, double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public int accountId() {
        return this.accountId;
    }

    public double amount() {
        return this.amount;
    }

    @Override
    public String toJson() {
        return format("{\"accountId\": \"%s\", \"amount\": \"%s\"}", accountId, amount);
    }
}
