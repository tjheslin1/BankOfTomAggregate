package io.github.tjheslin1.esb.infrastructure.mongo;

import io.github.tjheslin1.esb.domain.status.Status;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class MongoProbeResultTest implements WithAssertions {

    @Test
    public void formatsOKProbeResultForSuccessfulProbe() throws Exception {
        MongoProbeResult probeResult = new MongoProbeResult("ok");

        assertThat(probeResult.result()).isEqualTo(Status.OK);
        assertThat(probeResult.toJson()).isEqualTo("{\"dbStatus\": \"OK\"}");
    }

    @Test
    public void formatsFailProbeResultForFailedProbe() throws Exception {
        MongoProbeResult probeResult = new MongoProbeResult("fail");

        assertThat(probeResult.result()).isEqualTo(Status.FAIL);
        assertThat(probeResult.toJson()).isEqualTo("{\"dbStatus\": \"FAIL\"}");
    }
}