package io.github.tjheslin1.eventsourcedbanking.dao;

import com.mongodb.BasicDBObject;
import io.github.tjheslin1.WithMockito;
import io.github.tjheslin1.eventsourcedbanking.events.DepositFundsBalanceEvent;
import io.github.tjheslin1.eventsourcedbanking.events.WithdrawFundsBalanceEvent;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static io.github.tjheslin1.eventsourcedbanking.dao.JsonRenderer.renderDepositFundsEvent;
import static io.github.tjheslin1.eventsourcedbanking.dao.JsonRenderer.renderWithdrawFundsEvent;

public class JsonRendererTest implements WithMockito, WithAssertions {

    private DepositFundsBalanceEvent depositFundsEvent = mock(DepositFundsBalanceEvent.class);
    private WithdrawFundsBalanceEvent withdrawFundsEvent = mock(WithdrawFundsBalanceEvent.class);

    @Test
    public void renderDepositFundsEventTest() {
        when(depositFundsEvent.amount()).thenReturn(4);
        when(depositFundsEvent.timeOfEvent()).thenReturn("1970-01-01T00:00:00.001");

        BasicDBObject expectedDbDoc = new BasicDBObject("1970-01-01T00:00:00.001", new Object[]{4});
        BasicDBObject actualDbDoc = renderDepositFundsEvent(depositFundsEvent);

        assertThat(actualDbDoc).isEqualTo(expectedDbDoc);
    }

    @Test
    public void renderWithdrawFundsEventTest() {
        when(withdrawFundsEvent.amount()).thenReturn(4);
        when(withdrawFundsEvent.timeOfEvent()).thenReturn("1970-01-01T00:00:00.001");

        BasicDBObject expectedDbDoc = new BasicDBObject("1970-01-01T00:00:00.001", new Object[]{4});
        BasicDBObject actualDbDoc = renderWithdrawFundsEvent(withdrawFundsEvent);

        assertThat(actualDbDoc).isEqualTo(expectedDbDoc);
    }
}