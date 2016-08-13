package io.github.tjheslin1.aggregate.infrastructure.application.web.status;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.domain.status.ProbeResult;
import io.github.tjheslin1.aggregate.domain.status.Status;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;

public class StatusPageResultTest implements WithAssertions, WithMockito {

    private static final String OVERALL_RESULT = "OK";

    private ProbeResult probeResult = mock(ProbeResult.class);
    private List<ProbeResult> probeResults = singletonList(probeResult);
    private StatusPageResult statusPageResult = new StatusPageResult(probeResults, OVERALL_RESULT);

    @Before
    public void before() {
        when(probeResult.result()).thenReturn(Status.OK);
        when(probeResult.toJson()).thenReturn("{ \"result\": \'OK\"}");
    }

    @Test
    public void probeResultGetterTest() throws Exception {
        assertThat(statusPageResult.probeResults()).isEqualTo(probeResults);
    }

    @Test
    public void overallResultGetterTest() throws Exception {
        assertThat(statusPageResult.overallResult()).isEqualTo(OVERALL_RESULT);
    }
}