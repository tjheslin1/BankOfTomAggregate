package io.github.tjheslin1.eventsourcedbanking.dao;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.Test;

public class DepositFundsRendererTest implements WithMockito, WithAssertions {

    public static final String EXAMPLE_DATE = "1970-01-01T00:00:00.001";

    private DepositFundsBalanceEvent depositFundsEvent = mock(DepositFundsBalanceEvent.class);

    @Test
    public void renderDepositFundsEventTest() {
        when(depositFundsEvent.amount()).thenReturn(4);
        when(depositFundsEvent.timeOfEvent()).thenReturn(EXAMPLE_DATE);
        when(depositFundsEvent.collectionName()).thenReturn(DepositFundsBalanceEvent.class.getSimpleName());

        Document expectedDbDoc = new Document(EXAMPLE_DATE, depositFundsEvent.collectionName());
        expectedDbDoc.append("amount", 4);

        Document actualDbDoc = new DepositFundsRenderer().renderBalanceEvent(depositFundsEvent);
        assertThat(actualDbDoc).isEqualTo(expectedDbDoc);
    }
}