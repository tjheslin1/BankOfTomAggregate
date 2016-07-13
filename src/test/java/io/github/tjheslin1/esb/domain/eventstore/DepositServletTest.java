package io.github.tjheslin1.esb.domain.eventstore;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.application.usecases.DepositFundsUseCase;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequest;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequestJsonUnmarshaller;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class DepositServletTest implements WithAssertions, WithMockito {

    private static final int ACCOUNT_ID = 7;

    private final DepositRequestJsonUnmarshaller unmarshaller = mock(DepositRequestJsonUnmarshaller.class);
    private final DepositFundsUseCase depositFundsUseCase = mock(DepositFundsUseCase.class);

    private DepositServlet depositServlet = new DepositServlet(unmarshaller, depositFundsUseCase);

    private final DepositRequest depositRequest = mock(DepositRequest.class);
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final BufferedReader bufferedReader = mock(BufferedReader.class);

    @Test
    public void callsUseCaseSuccessfullyAndReturns200() throws Exception {
        String body = "{ \"accountId\": \"" + ACCOUNT_ID + "\", \"amount\": \"" + 45.0 + "\"}";
        when(unmarshaller.unmarshall(body)).thenReturn(depositRequest);

        when(request.getReader()).thenReturn(bufferedReader);
        when(request.getReader().lines()).thenReturn(Stream.of(body));

        depositServlet.doPost(request, response);

        verify(depositFundsUseCase).depositFunds(eq(depositRequest), (LocalDateTime) any());
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}