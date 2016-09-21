package io.github.tjheslin1.aggregate.infrastructure.domain.eventstore;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class BankingEventServer {

    private Server server;
    private ServletContextHandler context;

    public BankingEventServer(ServletContextHandler context, int serverPort) {
        this.context = context;
        this.server = new Server(serverPort);
    }

    public void start() throws Exception {
        server.setHandler(context);
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
