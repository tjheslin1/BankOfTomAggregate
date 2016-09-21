package io.github.tjheslin1.acceptance;

import okhttp3.Request;
import okio.Buffer;

import java.io.IOException;

import static io.github.tjheslin1.acceptance.JsonPrettyPrinter.prettyPrint;

public class RequestFormatter {

    public String format(Request request) {
        return String.format("%s %s%n%s%n%n%s", request.method(), request.url(), request.headers().toString(),
                requestBody(request));
    }

    private String requestBody(Request request) {
        if (request.body() == null) {
            return "";
        }

        Request copy = request.newBuilder().build();
        final Buffer buffer = new Buffer();
        try {
            copy.body().writeTo(buffer);
            return prettyPrint(buffer.readUtf8());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read request body.");
        }
    }
}
