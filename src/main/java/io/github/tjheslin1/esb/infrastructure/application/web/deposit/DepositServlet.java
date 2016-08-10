package io.github.tjheslin1.esb.infrastructure.application.web.deposit;

import io.github.tjheslin1.esb.application.usecases.DepositFundsUseCase;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.util.stream.Collectors.joining;

public class DepositServlet extends HttpServlet {

    private DepositRequestJsonUnmarshaller unmarshaller;
    private DepositFundsUseCase depositFundsUseCase;

    public DepositServlet(DepositFundsUseCase depositFundsUseCase, DepositRequestJsonUnmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
        this.depositFundsUseCase = depositFundsUseCase;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = request.getReader().lines().collect(joining(System.lineSeparator()));

        response.setContentType("application/json");

        try {
            DepositRequest depositRequest = unmarshaller.unmarshall(body);
            depositFundsUseCase.depositFunds(depositRequest, LocalDateTime.now());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
