package io.github.tjheslin1.esb.domain.eventstore;

import io.github.tjheslin1.esb.application.usecases.UseCase;
import io.github.tjheslin1.esb.infrastructure.application.usecases.DepositFundsUseCase;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequest;
import io.github.tjheslin1.esb.infrastructure.application.web.DepositRequestJsonUnmarshaller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.util.stream.Collectors.joining;

public class DepositServlet extends HttpServlet {

    private DepositRequestJsonUnmarshaller unmarshaller;
    private UseCase useCase;

    public DepositServlet(DepositRequestJsonUnmarshaller unmarshaller, UseCase useCase) {
        this.unmarshaller = unmarshaller;
        this.useCase = useCase;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = request.getReader().lines().collect(joining(System.lineSeparator()));
        DepositRequest depositRequest = unmarshaller.unmarshall(body);

        response.setContentType("application/json");

        try {
            ((DepositFundsUseCase) useCase).depositFunds(depositRequest, LocalDateTime.now());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
