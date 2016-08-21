package io.github.tjheslin1.acceptance.givens;

import io.github.theangrydev.yatspecfluent.Given;
import io.github.theangrydev.yatspecfluent.WriteOnlyTestItems;
import io.github.tjheslin1.acceptance.TestInfrastructure;

public class GivenTheEventStore implements Given {

    private final WriteOnlyTestItems writeOnlyTestItems;
    private final TestInfrastructure testInfrastructure;

    private int accountId;

    public GivenTheEventStore(WriteOnlyTestItems writeOnlyTestItems, TestInfrastructure testInfrastructure) {
        this.writeOnlyTestItems = writeOnlyTestItems;
        this.testInfrastructure = testInfrastructure;
    }

    public GivenTheEventStore contains() {
        return this;
    }

    public GivenTheEventStore noEventsForAccountWithId(int accountId) {
        return this;
    }

    @Override
    public void prime() {

    }
}
