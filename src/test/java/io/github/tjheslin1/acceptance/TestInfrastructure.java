package io.github.tjheslin1.acceptance;

import io.github.tjheslin1.aggregate.infrastructure.domain.eventstore.BankingEventServer;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import io.github.tjheslin1.aggregate.infrastructure.ui.Aggregate;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TestInfrastructure {

    private Settings settings = new Settings(Aggregate.loadProperties("localhost"));
    private BankingEventServer eventServer;

    public String serverBaseUrl() {
        return eventServer.baseUrl();
    }

    public void setUp() throws Exception {
        eventServer = new BankingEventServer(settings);
        eventServer.start();
    }

    public void tearDown() throws Exception {
        eventServer.stop();
    }

    public Response execute(Request request) {
        try {
            return new OkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
