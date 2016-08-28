package io.github.tjheslin1.acceptance;

import io.github.tjheslin1.aggregate.infrastructure.domain.eventstore.BankingEventServerBuilder;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import io.github.tjheslin1.aggregate.infrastructure.ui.Aggregate;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.IOException;

import static java.lang.String.format;

public class TestInfrastructure {

    private Settings settings = new Settings(Aggregate.loadProperties("localhost"));
    private BankingEventServerBuilder eventServerBuilder = new BankingEventServerBuilder(new ServletContextHandler(ServletContextHandler.NO_SESSIONS), settings);

    public BankingEventServerBuilder eventServerBuilder() {
        return eventServerBuilder;
    }

    public Settings settings() {
        return settings;
    }

    public String serverBaseUrl() {
        return format("http://%s:%s", settings.host(), settings.serverPort());
    }

    public Response execute(Request request) {
        try {
            return new OkHttpClient().newBuilder()
                    .retryOnConnectionFailure(true)
                    .build()
                    .newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
