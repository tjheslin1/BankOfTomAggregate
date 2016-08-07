package io.github.tjheslin1.esb.infrastructure.application.web.deposit;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class DepositRequestJsonUnmarshallerTest implements WithAssertions {

    private DepositRequestJsonUnmarshaller unmarshaller = new DepositRequestJsonUnmarshaller();

    @Test
    public void unmarshallsDepositRequest() {
        String exampleRequest = "{ \"accountId\": \"7\", \"amount\": \"50.0\"}";

        DepositRequest actualRequest = unmarshaller.unmarshall(exampleRequest);

        DepositRequest expectedRequest = new DepositRequest(7, 50.0);
        assertThat(actualRequest).isEqualTo(expectedRequest);
    }
}