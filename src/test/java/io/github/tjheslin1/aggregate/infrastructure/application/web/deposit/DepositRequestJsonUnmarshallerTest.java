package io.github.tjheslin1.aggregate.infrastructure.application.web.deposit;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import static java.lang.String.format;

public class DepositRequestJsonUnmarshallerTest implements WithAssertions {

    public static final int ACCOUNT_ID = 7;
    public static final double AMOUNT = 50.0;

    private DepositRequestJsonUnmarshaller unmarshaller = new DepositRequestJsonUnmarshaller();

    @Test
    public void unmarshallsDepositRequest() {
        DepositRequest expectedRequest = new DepositRequest(ACCOUNT_ID, AMOUNT);
        String exampleRequest = format(expectedRequest.toJson(), ACCOUNT_ID, AMOUNT);

        DepositRequest actualRequest = unmarshaller.unmarshall(exampleRequest);

        assertThat(actualRequest.accountId()).isEqualTo(expectedRequest.accountId());
        assertThat(actualRequest.amount()).isEqualTo(expectedRequest.amount());
    }
}