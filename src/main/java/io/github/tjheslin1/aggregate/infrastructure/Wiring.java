package io.github.tjheslin1.aggregate.infrastructure;

import com.mongodb.MongoClient;
import io.github.tjheslin1.aggregate.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.aggregate.application.probe.Probe;
import io.github.tjheslin1.aggregate.application.usecases.DepositFundsUseCase;
import io.github.tjheslin1.aggregate.application.usecases.StatusUseCase;
import io.github.tjheslin1.aggregate.infrastructure.application.cqrs.MongoBalanceCommandWriter;
import io.github.tjheslin1.aggregate.infrastructure.mongo.MongoConnection;
import io.github.tjheslin1.aggregate.infrastructure.mongo.MongoProbe;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.List;

import static java.util.Arrays.asList;

public class Wiring {

    public final Settings settings;

    public Wiring(Settings settings) {
        this.settings = settings;
    }

    public DepositFundsUseCase depositFundsUseCase() {
        return new DepositFundsUseCase(balanceCommandWriter());
    }

    public StatusUseCase statusUseCase(List<Probe> probes) {
        return new StatusUseCase(probes);
    }

    public ServletContextHandler servletContextHandler() {
        return new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
    }

    private BalanceCommandWriter balanceCommandWriter() {
        return new MongoBalanceCommandWriter(mongoClient(), settings);
    }

    private MongoClient mongoClient() {
        return MongoConnection.mongoClient(settings);
    }

    public Probe mongoProbe() {
        return new MongoProbe(mongoClient(), settings);
    }
}
