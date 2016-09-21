package io.github.tjheslin1.aggregate.infrastructure.application.web.status;

import io.github.tjheslin1.aggregate.application.usecases.StatusUseCase;
import io.github.tjheslin1.aggregate.application.web.JsonMarshaller;
import io.github.tjheslin1.aggregate.domain.status.ProbeResult;
import io.github.tjheslin1.aggregate.domain.status.Status;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

        List<ProbeResult> probeResultList = statusUseCase.checkStatusProbes();

        Status overallStatus = probeResultList.stream()
                .anyMatch(probeResult -> Status.FAIL.equals(probeResult.result())) ? Status.FAIL : Status.OK;

        String responseBody = marshaller.marshall(new StatusPageResult(probeResultList, overallStatus.value));

        if (Status.OK.equals(overallStatus)) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(responseBody);
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write(responseBody);
        }
    }
}
