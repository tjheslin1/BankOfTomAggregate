package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.BasicDBObject;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class DepositFundsRendererTest implements WithMockito, WithAssertions {

    public static final String EXAMPLE_DATE = "1970-01-01T00:00:00.001";

    private DepositFundsBalanceEvent depositFundsEvent = mock(DepositFundsBalanceEvent.class);

    @Test
    public void renderDepositFundsEventTest() {
        when(depositFundsEvent.amount()).thenReturn(4);
        when(depositFundsEvent.timeOfEvent()).thenReturn(EXAMPLE_DATE);

        BasicDBObject expectedDbDoc = new BasicDBObject(EXAMPLE_DATE, DepositFundsBalanceEvent.class.getSimpleName());
        expectedDbDoc.append("amount", 4);

        BasicDBObject actualDbDoc = new DepositFundsRenderer().renderBalanceEvent(depositFundsEvent);

        assertThat(actualDbDoc).isEqualTo(expectedDbDoc);
    }
}