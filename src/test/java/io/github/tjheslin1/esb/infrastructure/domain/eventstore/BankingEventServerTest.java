package io.github.tjheslin1.esb.infrastructure.domain.eventstore;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.domain.eventstore.DepositServlet;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequest;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;
import io.github.tjheslin1.esb.infrastructure.settings.TestSettings;
import org.assertj.core.api.WithAssertions;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.String.format;

public class BankingEventServerTest implements WithAssertions, WithMockito {

    private Settings settings = new TestSettings();
    private BankingEventServer server;

    @Before
    public void startServer() throws Exception {
        server = new BankingEventServer(settings);
        server.withContext(DepositServlet.class, "/deposit");

        server.start();
    }

    @After
    public void stopJetty() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPost() throws Exception {
        URL url = new URL(format("%s%s:%s/deposit", settings.webProtocol(), settings.host(), settings.serverPort()));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");

        connection.getOutputStream().write(new DepositRequest(7, 45.0).toJson().getBytes("UTF-8"));

        connection.connect();
        assertThat(connection.getResponseCode()).isEqualTo(HttpStatus.OK_200);
    }
}