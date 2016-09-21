package io.github.tjheslin1.acceptance;

import io.github.theangrydev.yatspecfluent.ThenFactory;
import io.github.tjheslin1.acceptance.givens.GivenTheStatusPage;
import io.github.tjheslin1.acceptance.thens.ThenTheResponse;
import io.github.tjheslin1.acceptance.whens.WhenTheStatusPageIsChecked;
import io.github.tjheslin1.aggregate.application.probe.Probe;
import io.github.tjheslin1.aggregate.infrastructure.Wiring;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

public class StatusTest extends AcceptanceTest<Request, Response> {

    private Wiring wiring = new Wiring(testInfrastructure.settings());

    private final Probe DATABASE_PROBE = wiring.mongoProbe();

    private final GivenTheStatusPage theStatusPage = new GivenTheStatusPage(testInfrastructure);
    private final WhenTheStatusPageIsChecked theStatusPageIsChecked = new WhenTheStatusPageIsChecked(this, testInfrastructure, "Gateway",
            new RequestFormatter(), new ResponseFormatter());
    private final ThenFactory<ThenTheResponse, Response> theResponse = ThenTheResponse::new;

    @Test
    public void statusPageTest() throws Exception {
        given(theStatusPage.containsProbe(DATABASE_PROBE));
        when(theStatusPageIsChecked);
        then(theResponse).willReturn().withResponseCode(200);
    }
}
