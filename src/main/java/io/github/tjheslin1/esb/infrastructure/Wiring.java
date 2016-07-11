package io.github.tjheslin1.esb.infrastructure;

import com.mongodb.MongoClient;
import io.github.tjheslin1.esb.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.esb.application.usecases.DepositFundsUseCase;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.command.MongoBalanceCommandWriter;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;

public class Wiring {

    private Settings settings;

    public Wiring(Settings settings) {
        this.settings = settings;
    }

    DepositFundsUseCase depositFundsUseCase() {
        return new DepositFundsUseCase(balanceCommandWriter(mongoClient()));
    }

    private BalanceCommandWriter balanceCommandWriter(MongoClient mongoClient) {
        return new MongoBalanceCommandWriter(mongoClient, settings);
    }

    private MongoClient mongoClient() {
        return new MongoClient(settings.host());
    }
}
