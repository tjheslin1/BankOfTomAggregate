package io.github.tjheslin1.esb.infrastructure.application.web.status;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.esb.infrastructure.mongo.MongoProbeResult;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static java.util.Collections.singletonList;

public class StatusResponseJsonMarshallerTest implements WithAssertions, WithMockito {

    private final StatusPageResult statusPageResult = mock(StatusPageResult.class);

    private StatusResponseJsonMarshaller marshaller = new StatusResponseJsonMarshaller();

    @Test
    public void rendersExpectedStatusPage() throws Exception {
        when(statusPageResult.probeResults()).thenReturn(singletonList(new MongoProbeResult("ok")));
        when(statusPageResult.overallResult()).thenReturn("OK");

        assertThat(marshaller.marshall(statusPageResult)).isEqualTo("{ probes: [{\"dbStatus\": \"OK\"}], \"overallStatus\": \"OK\"}");
    }
}