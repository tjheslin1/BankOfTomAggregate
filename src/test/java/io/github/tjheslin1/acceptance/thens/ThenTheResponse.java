package io.github.tjheslin1.acceptance.thens;

import okhttp3.Response;
import org.assertj.core.api.WithAssertions;

import java.io.IOException;

public class ThenTheResponse implements WithAssertions {

    private final int responseCode;

    public ThenTheResponse(Response response) {
        responseCode = response.code();
    }

    public ThenTheResponse withResponseCode(int code) {
        assertThat(responseCode).isEqualTo(code);
        return this;
    }


    public ThenTheResponse willReturn() {
        return this;
    }

    private String responseBody(Response response) {
        try {
            return response.body().string();
        } catch (IOException e) {
            throw new IllegalStateException("Could not read body from response: " + response);
        }
    }
}
