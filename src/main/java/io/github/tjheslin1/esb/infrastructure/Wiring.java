package io.github.tjheslin1.esb.infrastructure;

import com.mongodb.MongoClient;
import io.github.tjheslin1.esb.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.esb.application.usecases.DepositFundsUseCase;
import io.github.tjheslin1.esb.application.usecases.StatusUseCase;
import io.github.tjheslin1.esb.infrastructure.application.cqrs.MongoBalanceCommandWriter;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;

import java.util.stream.Stream;

public class Wiring {

    public final Settings settings;

    public Wiring(Settings settings) {
        this.settings = settings;
    }

    public DepositFundsUseCase depositFundsUseCase() {
        return new DepositFundsUseCase(balanceCommandWriter(mongoClient()));
    }

    public StatusUseCase statusUseCase() {
        return new StatusUseCase(Stream.empty());
    }

    private BalanceCommandWriter balanceCommandWriter(MongoClient mongoClient) {
        return new MongoBalanceCommandWriter(mongoClient, settings);
    }

    private MongoClient mongoClient() {
        return new MongoClient(settings.host());
    }
}
