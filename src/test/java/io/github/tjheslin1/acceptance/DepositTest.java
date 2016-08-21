package io.github.tjheslin1.acceptance;

import io.github.theangrydev.yatspecfluent.ThenFactory;
import io.github.tjheslin1.acceptance.givens.GivenTheEventStore;
import io.github.tjheslin1.acceptance.thens.ThenTheResponse;
import io.github.tjheslin1.acceptance.whens.WhenADepositIsMade;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

public class DepositTest extends AcceptanceTest<Request, Response> {

    public static final int ACCOUNT_ID = 23;
    public static final double AMOUNT = 51.0;

    private GivenTheEventStore theEventStore = new GivenTheEventStore(this, testInfrastructure);
    private WhenADepositIsMade aDepositIsMade = new WhenADepositIsMade(this, testInfrastructure, "Gateway");
    private ThenFactory<ThenTheResponse, Response> theResponse = ThenTheResponse::new;

    @Test
    public void depositIsMadeTest() throws Exception {
        given(theEventStore.contains().noEventsForAccountWithId(ACCOUNT_ID));
        when(aDepositIsMade.forAccountWithId(ACCOUNT_ID).withAmount(AMOUNT));
        then(theResponse).willReturn().withBody("").withResponseCode(200);
    }
}
