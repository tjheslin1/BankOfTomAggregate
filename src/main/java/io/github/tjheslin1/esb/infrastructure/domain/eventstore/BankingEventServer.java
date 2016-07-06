package io.github.tjheslin1.esb.infrastructure.domain.eventstore;

import io.github.tjheslin1.esb.infrastructure.settings.Settings;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.http.HttpServlet;

public class BankingEventServer {

    private Server server;
    private ServletContextHandler context;

    public BankingEventServer(Settings settings) {
        this.server = new Server(settings.serverPort());
        this.context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
    }

    public BankingEventServer withContext(Class<? extends HttpServlet> servletClass, String path) {
        context.addServlet(servletClass, path);
        return this;
    }

    public void start() throws Exception {
        server.setHandler(context);
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
