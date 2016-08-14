package io.github.tjheslin1.aggregate.infrastructure.domain.eventstore;

import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;

import static java.lang.String.format;

public class BankingEventServer {

    private Server server;
    private ServletContextHandler context;
    private Settings settings;

    public BankingEventServer(Settings settings) {
        this.settings = settings;
        this.server = new Server(settings.serverPort());

        this.context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        this.context.setContextPath("/");
    }

    public BankingEventServer withServlet(HttpServlet servlet, String path) {
        context.addServlet(new ServletHolder(servlet), path);
        return this;
    }

    public void start() throws Exception {
        server.setHandler(context);
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public String baseUrl() {
        return format("http://%s:%s", settings.host(), settings.serverPort());
    }
}
