package io.github.tjheslin1.esb.infrastructure.application.web.status;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.application.usecases.StatusUseCase;
import io.github.tjheslin1.esb.application.web.JsonMarshaller;
import io.github.tjheslin1.esb.domain.status.ProbeResult;
import io.github.tjheslin1.esb.domain.status.Status;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class StatusServletTest implements WithAssertions, WithMockito {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final StatusResponseJsonMarshaller marshaller = mock(StatusResponseJsonMarshaller.class);
    private final PrintWriter printWriter = mock(PrintWriter.class);

    private final ProbeResult firstOKProbeResult = mock(ProbeResult.class);
    private final ProbeResult secondOKProbeResult = mock(ProbeResult.class);
    private final ProbeResult firstFailProbeResult = mock(ProbeResult.class);

    private final StatusUseCase statusUseCase = mock(StatusUseCase.class);
    private final StatusPageResult statusPageResult = mock(StatusPageResult.class);

    private StatusServlet statusServlet = new StatusServlet(statusUseCase, marshaller);

    @Before
    public void before() {
//        when(firstOKProbeResult.result()).thenReturn(Status.OK);
//        when(firstOKProbeResult.toJson()).thenReturn("{\"result\": \"OK\"}");
//
//        when(secondOKProbeResult.result()).thenReturn(Status.OK);
//        when(secondOKProbeResult.toJson()).thenReturn("{\"result\": \"OK\"}");
//
        when(firstFailProbeResult.result()).thenReturn(Status.FAIL);
        when(firstFailProbeResult.toJson()).thenReturn("{\"result\": \"FAIL\"}");
    }

    @Test
    public void returns200IfAllProbesAreOK() throws Exception {
        List<ProbeResult> probeResults = asList(firstOKProbeResult, secondOKProbeResult);

        when(statusUseCase.checkStatusProbes()).thenReturn(probeResults);
        when(response.getWriter()).thenReturn(printWriter);

        String expectedBody = "{ probes: [{\"result\": \"OK\"}, {\"result\": \"OK\"}], \"overallStatus\": \"OK\"}";
        when(marshaller.marshall(any())).thenReturn(expectedBody);

        statusServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(printWriter).write(expectedBody);
    }

    @Test
    public void returns409IfAnyProbesAreFail() throws Exception {
        List<ProbeResult> probeResults = asList(firstOKProbeResult, firstFailProbeResult);

        when(statusUseCase.checkStatusProbes()).thenReturn(probeResults);
        when(response.getWriter()).thenReturn(printWriter);

        String expectedBody = "{ \"probes\": [{\"result\": \"OK\"}, {\"result\": \"FAIL\"}], \"overallStatus\": \"FAIL\"}";
        when(marshaller.marshall(any())).thenReturn(expectedBody);

        statusServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_CONFLICT);
        verify(printWriter).write(expectedBody);
    }

    @Test
    public void returns200IfThereAreNoProbes() throws Exception {
        List<ProbeResult> probeResults = emptyList();

        when(statusUseCase.checkStatusProbes()).thenReturn(probeResults);
        when(response.getWriter()).thenReturn(printWriter);

        String expectedBody = "{ probes: [], \"overallStatus\": \"OK\"}";
        when(marshaller.marshall(any())).thenReturn(expectedBody);

        statusServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(printWriter).write(expectedBody);
    }
}