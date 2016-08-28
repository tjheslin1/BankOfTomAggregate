package io.github.tjheslin1.aggregate.infrastructure.domain.eventstore;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.infrastructure.application.web.deposit.DepositRequest;
import io.github.tjheslin1.aggregate.infrastructure.application.web.deposit.DepositRequestJsonUnmarshaller;
import io.github.tjheslin1.aggregate.infrastructure.application.web.deposit.DepositServlet;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.String.format;

public class BankingEventServerTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 7;
    private static final double AMOUNT = 45.0;

    private DepositRequestJsonUnmarshaller unmarshaller = mock(DepositRequestJsonUnmarshaller.class);
    private DepositServlet depositServlet = mock(DepositServlet.class);

    private Settings settings = mock(Settings.class);
    private BankingEventServer server;

    @Before
    public void startServer() throws Exception {
        when(settings.serverPort()).thenReturn(8085);
        when(unmarshaller.unmarshall(format("{\"accountId\": \"%s\", \"amount\": \"%s\"}", ACCOUNT_ID, AMOUNT))).thenReturn(new DepositRequest(7, 45.0));

        server = new BankingEventServerBuilder(new ServletContextHandler(ServletContextHandler.NO_SESSIONS), settings)
                .withServlet(new ServletHolder(depositServlet), "/deposit")
                .build();

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

    // TODO this test is brittle
    @Test
    public void testPost() throws Exception {
        URL url = new URL("http://localhost:8085/deposit");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");

        connection.getOutputStream().write(new DepositRequest(ACCOUNT_ID, AMOUNT).toJson().getBytes("UTF-8"));

        connection.connect();
        assertThat(connection.getResponseCode()).isEqualTo(HttpStatus.OK_200);
    }
}