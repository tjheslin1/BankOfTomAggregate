package io.github.tjheslin1.aggregate.infrastructure;

import com.mongodb.MongoClient;
import io.github.tjheslin1.aggregate.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.aggregate.application.usecases.DepositFundsUseCase;
import io.github.tjheslin1.aggregate.application.usecases.StatusUseCase;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.MongoBalanceCommandWriter;
import io.github.tjheslin1.aggregate.infrastructure.mongo.MongoConnection;
import io.github.tjheslin1.aggregate.infrastructure.mongo.MongoProbe;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;

import static java.util.Arrays.asList;

public class Wiring {

    public final Settings settings;

    public Wiring(Settings settings) {
        this.settings = settings;
    }

    public DepositFundsUseCase depositFundsUseCase() {
        return new DepositFundsUseCase(balanceCommandWriter());
    }

    public StatusUseCase statusUseCase() {
        return new StatusUseCase(asList(new MongoProbe(mongoClient(), settings)));
    }

    private BalanceCommandWriter balanceCommandWriter() {
        return new MongoBalanceCommandWriter(mongoClient(), settings);
    }

    private MongoClient mongoClient() {
        return MongoConnection.mongoClient(settings);
    }
}
