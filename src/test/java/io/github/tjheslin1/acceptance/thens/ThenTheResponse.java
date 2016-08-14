package io.github.tjheslin1.acceptance.thens;

import okhttp3.Response;
import org.assertj.core.api.WithAssertions;

import java.io.IOException;

public class ThenTheResponse implements WithAssertions {

    private final String responseBody;
    private final int responseCode;

    public ThenTheResponse(Response response) {
        responseBody = responseBody(response);
        responseCode = response.code();
    }

    private String responseBody(Response response) {
        try {
            return response.body().string();
        } catch (IOException e) {
            throw new IllegalStateException("Could not read body from response: " + response);
        }
    }

    public ThenTheResponse withBody(String response) {
        assertThat(responseBody).isEqualTo(response);
        return this;
    }


    public ThenTheResponse withResponseCode(int code) {
        assertThat(responseCode).isEqualTo(code);
        return this;
    }

    public ThenTheResponse willReturn() {
        return this;
    }
}
