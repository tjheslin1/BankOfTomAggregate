package io.github.tjheslin1.aggregate.infrastructure.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.aggregate.domain.status.ProbeResult;
import io.github.tjheslin1.aggregate.domain.status.Status;
import io.github.tjheslin1.aggregate.infrastructure.settings.Settings;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

public class MongoProbeTest implements WithAssertions, WithMockito {

    private final MongoDatabase mongoDatabase = mock(MongoDatabase.class);
    private final MongoClient mongoClient = mock(MongoClient.class);
    private final Settings settings = mock(Settings.class);

    private MongoProbe probe = new MongoProbe(mongoClient, settings);

    @Before
    public void before() {
        when(settings.mongoDbName()).thenReturn("blah");
        when(mongoClient.getDatabase("blah")).thenReturn(mongoDatabase);
    }

    @Test
    public void shouldProbeDatabaseForHealthCheckAndReturnOK() throws Exception {
        when(mongoDatabase.runCommand(any())).thenReturn(new Document("ok", 1));

        ProbeResult probeResult = probe.probe();

        assertThat(probeResult.result()).isEqualTo(Status.OK);
    }

    @Test
    public void shouldProbeDatabaseForHealthCheckAndReturnFAIL() throws Exception {
        when(mongoDatabase.runCommand(any())).thenReturn(new Document("fail", 1));

        ProbeResult probeResult = probe.probe();

        assertThat(probeResult.result()).isEqualTo(Status.FAIL);
    }
}