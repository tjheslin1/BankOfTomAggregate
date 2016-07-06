package io.github.tjheslin1.esb.infrastructure.application.web;

import io.github.tjheslin1.esb.application.web.Request;

import static java.lang.String.format;

public class DepositRequest implements Request {

    private int accountId;
    private double amount;

    public DepositRequest(int accountId, double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    @Override
    public String toJson() {
        return format("{\"accountId\": \"%s\", \"amount\": \"%s\"}", accountId, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepositRequest that = (DepositRequest) o;

        return accountId == that.accountId && Double.compare(that.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = accountId;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
