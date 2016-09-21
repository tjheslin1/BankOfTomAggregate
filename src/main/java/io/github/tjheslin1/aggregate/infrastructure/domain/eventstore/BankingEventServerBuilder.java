package io.github.tjheslin1.aggregate.infrastructure.domain.eventstore;

import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class BankingEventServerBuilder {

    private ServletContextHandler context;
    private Settings settings;

    public BankingEventServerBuilder(ServletContextHandler context, Settings settings) {
        this.context = context;
        this.context.setContextPath("/");

        this.settings = settings;
    }

    public BankingEventServerBuilder withServlet(ServletHolder servlet, String path) {
        context.addServlet(servlet, path);
        return this;
    }

    public BankingEventServer build() {
        return new BankingEventServer(context, settings.serverPort());
    }
}
