package io.github.tjheslin1.esb.infrastructure.domain.eventstore;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.application.cqrs.command.BalanceCommandWriter;
import io.github.tjheslin1.esb.application.usecases.DepositFundsUseCase;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositServlet;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequest;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequestJsonUnmarshaller;
import io.github.tjheslin1.esb.infrastructure.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

public class BankingEventServerTest implements WithAssertions, WithMockito {

    private DepositRequestJsonUnmarshaller unmarshaller = mock(DepositRequestJsonUnmarshaller.class);
    private BalanceCommandWriter commandWriter = mock(BalanceCommandWriter.class);

    private Settings settings = mock(Settings.class);
    private BankingEventServer server;

    @Before
    public void startServer() throws Exception {
        when(settings.serverPort()).thenReturn(8085);
        when(unmarshaller.unmarshall("{\"accountId\": \"7\", \"amount\": \"45.0\"}")).thenReturn(new DepositRequest(7, 45.0));

        server = new BankingEventServer(settings);
        server.withServlet(new DepositServlet(unmarshaller, new DepositFundsUseCase(commandWriter)), "/deposit");

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
        URL url = new URL("http://localhost:8085/deposit");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");

        connection.getOutputStream().write(new DepositRequest(7, 45.0).toJson().getBytes("UTF-8"));

        connection.connect();
        assertThat(connection.getResponseCode()).isEqualTo(HttpStatus.OK_200);
    }
}