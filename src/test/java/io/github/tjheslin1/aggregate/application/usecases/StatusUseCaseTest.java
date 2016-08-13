package io.github.tjheslin1.aggregate.application.usecases;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.application.probe.Probe;
import io.github.tjheslin1.aggregate.domain.status.ProbeResult;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;

public class StatusUseCaseTest implements WithAssertions, WithMockito {

    private Probe probe = mock(Probe.class);
    private ProbeResult probeResult = mock(ProbeResult.class);

    private List<Probe> probes = singletonList(probe);
    private StatusUseCase statusUseCase = new StatusUseCase(probes);

    @Before
    public void before() {
        when(probe.probe()).thenReturn(probeResult);
    }

    @Test
    public void mapsProbesToProbeResults() throws Exception {
        List<ProbeResult> expectedProbeResults = singletonList(probeResult);

        assertThat(statusUseCase.checkStatusProbes()).isEqualTo(expectedProbeResults);
    }
}