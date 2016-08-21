package io.github.tjheslin1.acceptance;

import io.github.theangrydev.yatspecfluent.ThenFactory;
import io.github.tjheslin1.acceptance.givens.GivenTheStatusPage;
import io.github.tjheslin1.acceptance.thens.ThenTheResponse;
import io.github.tjheslin1.acceptance.whens.WhenTheStatusPageIsChecked;
import io.github.tjheslin1.aggregate.infrastructure.Wiring;
import io.github.tjheslin1.aggregate.infrastructure.application.web.deposit.DepositRequestJsonUnmarshaller;
import io.github.tjheslin1.aggregate.infrastructure.application.web.deposit.DepositServlet;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServlet;

public class StatusTest extends AcceptanceTest<Request, Response> {

    private Wiring wiring = new Wiring(testInfrastructure.settings());

    private final HttpServlet STATUS_SERVLET = new DepositServlet(wiring.depositFundsUseCase(), new DepositRequestJsonUnmarshaller());

    private GivenTheStatusPage theStatusPage = new GivenTheStatusPage(this, testInfrastructure);
    private WhenTheStatusPageIsChecked theStatusPageIsChecked = new WhenTheStatusPageIsChecked(this, testInfrastructure, "Gateway");
    private ThenFactory<ThenTheResponse, Response> theResponse = ThenTheResponse::new;

    @Before
    public void before() {
    }

    @Test
    public void statusPageTest() throws Exception {
        given(theStatusPage.containsProbe(STATUS_SERVLET, "/status"));
        when(theStatusPageIsChecked);
        then(theResponse).willReturn()
                .withBody("{ \"probes\": [{\"dbStatus\": \"OK\"}], \"overallStatus\": \"OK\"}")
                .withResponseCode(200);
    }
}
