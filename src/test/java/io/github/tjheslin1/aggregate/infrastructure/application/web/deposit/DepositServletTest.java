package io.github.tjheslin1.aggregate.infrastructure.application.web.deposit;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.application.usecases.DepositFundsUseCase;
import org.assertj.core.api.WithAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static java.lang.String.format;

public class DepositServletTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 7;

    private final DepositRequestJsonUnmarshaller unmarshaller = mock(DepositRequestJsonUnmarshaller.class);
    private final DepositFundsUseCase depositFundsUseCase = mock(DepositFundsUseCase.class);

    private final DepositRequest depositRequest = mock(DepositRequest.class);
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final BufferedReader bufferedReader = mock(BufferedReader.class);

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream oldErr = System.err;

    @Before
    public void before() {
        System.setErr(new PrintStream(outContent));
    }

    @After
    public void after() {
        System.setErr(oldErr);
    }

    @Test
    public void callsUseCaseSuccessfullyAndReturns200() throws Exception {
        DepositServlet depositServlet = new DepositServlet(depositFundsUseCase, unmarshaller);

        String body = format("{ \"accountId\": \"%s\", \"amount\": \"%s\"}", ACCOUNT_ID, 45.0);
        when(unmarshaller.unmarshall(body)).thenReturn(depositRequest);

        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getReader().lines()).thenReturn(Stream.of(body));

        depositServlet.doPost(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_OK);

        verify(depositFundsUseCase).depositFunds(eq(depositRequest), any());
    }

    @Test
    public void returnsErrorCode409OnBadRequest() throws Exception {
        DepositServlet depositServlet = new DepositServlet(depositFundsUseCase, new DepositRequestJsonUnmarshaller());

        String badBody = "{}";
        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getReader().lines()).thenReturn(Stream.of(badBody));

        depositServlet.doPost(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_CONFLICT);

        assertThat(outContent.toString())
                .startsWith("org.json.JSONException: Error unmarshalling DepositRequest with request body: {}");
    }
}