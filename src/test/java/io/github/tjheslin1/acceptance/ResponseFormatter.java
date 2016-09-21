package io.github.tjheslin1.acceptance;

import okhttp3.Response;

import java.io.IOException;

import static io.github.tjheslin1.acceptance.JsonPrettyPrinter.prettyPrint;

public class ResponseFormatter {

    public String format(Response response) throws IOException {
        return String.format("%s%n%n%s%n%n%s", response.code(), response.headers().toString(), responseBody(response));
    }

    private String responseBody(Response response) throws IOException {
        String body = response.body().string();
        return prettyPrint(body);
    }
}
