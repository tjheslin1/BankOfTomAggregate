package io.github.tjheslin1.aggregate.infrastructure.domain.eventstore;

import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;

public class BankingEventServer {

    private Server server;
    private ServletContextHandler context;

    public BankingEventServer(Settings settings) {
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
        return server.getURI().toString();
    }
}
