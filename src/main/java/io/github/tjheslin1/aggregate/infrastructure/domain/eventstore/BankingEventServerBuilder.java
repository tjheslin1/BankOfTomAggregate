package io.github.tjheslin1.aggregate.infrastructure.domain.eventstore;

import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;

public class BankingEventServerBuilder {

    private ServletContextHandler context;
    private Settings settings;

    public BankingEventServerBuilder(Settings settings) {
        this.context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        this.context.setContextPath("/");

        this.settings = settings;
    }

    public BankingEventServerBuilder withServlet(HttpServlet servlet, String path) {
        context.addServlet(new ServletHolder(servlet), path);
        return this;
    }

    public BankingEventServer build() {
        return new BankingEventServer(context, settings);
    }
}
