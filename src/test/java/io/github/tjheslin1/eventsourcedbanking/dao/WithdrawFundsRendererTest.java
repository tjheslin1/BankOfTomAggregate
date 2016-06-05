package io.github.tjheslin1.eventsourcedbanking.dao;

import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;
import org.assertj.core.api.WithAssertions;
import org.bson.Document;
import org.junit.Test;

public class WithdrawFundsRendererTest implements WithMockito, WithAssertions {

    public static final String EXAMPLE_DATE = "1970-01-01T00:00:00:001";

    private WithdrawFundsBalanceEvent withdrawFundsEvent = mock(WithdrawFundsBalanceEvent.class);

    @Test
    public void renderWithdrawFundsEventTest() {
        when(withdrawFundsEvent.amount()).thenReturn(4);
        when(withdrawFundsEvent.timeOfEvent()).thenReturn(EXAMPLE_DATE);
        when(withdrawFundsEvent.collectionName()).thenReturn(WithdrawFundsBalanceEvent.class.getSimpleName());

        Document expectedDbDoc = new Document("timeOfEvent", EXAMPLE_DATE);
        expectedDbDoc.append("amount", 4);

        Document actualDbDoc = new WithdrawFundsMarshaller().renderBalanceEvent(withdrawFundsEvent);
        assertThat(actualDbDoc).isEqualTo(expectedDbDoc);
    }
}