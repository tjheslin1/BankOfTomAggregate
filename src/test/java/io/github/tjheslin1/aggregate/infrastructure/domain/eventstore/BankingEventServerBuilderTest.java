package io.github.tjheslin1.aggregate.infrastructure.domain.eventstore;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Test;

import javax.servlet.http.HttpServlet;

public class BankingEventServerBuilderTest implements WithAssertions, WithMockito {

    private final ServletContextHandler context = mock(ServletContextHandler.class);
    private final Settings settings = mock(Settings.class);
    private final HttpServlet httpServlet = mock(HttpServlet.class);

    @Test
    public void setsUpContext() throws Exception {
        new BankingEventServerBuilder(context, settings);

        verify(context).setContextPath("/");
    }

    @Test
    public void appendsServlet() throws Exception {
        BankingEventServerBuilder eventServerBuilder = new BankingEventServerBuilder(context, settings);

        String path = "/status";
        ServletHolder servletHolder = new ServletHolder(httpServlet);

        eventServerBuilder.withServlet(servletHolder, path);

        verify(context).addServlet(servletHolder, path);
    }
}