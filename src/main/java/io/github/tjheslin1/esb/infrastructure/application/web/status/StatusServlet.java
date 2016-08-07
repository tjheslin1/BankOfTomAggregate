package io.github.tjheslin1.esb.infrastructure.application.web.status;

import io.github.tjheslin1.esb.application.usecases.StatusUseCase;
import io.github.tjheslin1.esb.application.web.JsonMarshaller;
import io.github.tjheslin1.esb.domain.status.ProbeResult;
import io.github.tjheslin1.esb.domain.status.Status;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Stream;

public class StatusServlet extends HttpServlet {

    private StatusUseCase statusUseCase;
    private JsonMarshaller marshaller;

    public StatusServlet(StatusUseCase statusUseCase, JsonMarshaller marshaller) {
        this.statusUseCase = statusUseCase;
        this.marshaller = marshaller;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        Stream<ProbeResult> probeResults = statusUseCase.checkStatusProbes();
        String responseBody = marshaller.marshall(probeResults);

        Status overallStatus = probeResults.anyMatch(probeResult -> Status.FAIL.equals(probeResult.result())) ? Status.FAIL : Status.OK;

        if (Status.OK.equals(overallStatus)) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(responseBody);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write(responseBody);
        }
    }
}
