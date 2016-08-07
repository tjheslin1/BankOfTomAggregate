package io.github.tjheslin1.esb.application.usecases;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.application.probe.Probe;
import io.github.tjheslin1.esb.domain.Status;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

public class StatusUseCaseTest implements WithAssertions, WithMockito {

    private final Probe firstOKProbe = mock(Probe.class);
    private final Probe secondOKProbe = mock(Probe.class);
    private final Probe firstFailProbe = mock(Probe.class);

    @Before
    public void before() {
        when(firstOKProbe.probe()).thenReturn(Status.OK);
        when(secondOKProbe.probe()).thenReturn(Status.OK);
        when(firstFailProbe.probe()).thenReturn(Status.FAIL);
    }

    @Test
    public void returnsOKIfAllProbesAreOK() throws Exception {
        Stream<Probe> probes = Stream.of(firstOKProbe, secondOKProbe);
        StatusUseCase statusUseCase = new StatusUseCase(probes);

        assertThat(statusUseCase.checkStatus()).isEqualTo(Status.OK);
    }

    @Test
    public void returnsFailIfAnyProbesAreFail() throws Exception {
        Stream<Probe> probes = Stream.of(firstOKProbe, secondOKProbe, firstFailProbe);
        StatusUseCase statusUseCase = new StatusUseCase(probes);

        assertThat(statusUseCase.checkStatus()).isEqualTo(Status.FAIL);
    }

    @Test
    public void returnsOkIfThereAreNoProbes() throws Exception {
        Stream<Probe> probes = Stream.empty();
        StatusUseCase statusUseCase = new StatusUseCase(probes);

        assertThat(statusUseCase.checkStatus()).isEqualTo(Status.OK);
    }
}