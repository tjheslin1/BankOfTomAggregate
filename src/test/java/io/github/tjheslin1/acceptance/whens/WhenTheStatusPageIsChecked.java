package io.github.tjheslin1.acceptance.whens;

import io.github.theangrydev.yatspecfluent.When;
import io.github.theangrydev.yatspecfluent.WriteOnlyTestItems;
import io.github.tjheslin1.acceptance.RequestFormatter;
import io.github.tjheslin1.acceptance.ResponseFormatter;
import io.github.tjheslin1.acceptance.TestInfrastructure;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static java.lang.String.format;

public class WhenTheStatusPageIsChecked implements When<Request, Response> {

    private final WriteOnlyTestItems writeOnlyTestItems;
    private final TestInfrastructure testInfrastructure;
    private final String caller;
    private RequestFormatter requestFormatter;
    private ResponseFormatter responseFormatter;

    public WhenTheStatusPageIsChecked(WriteOnlyTestItems writeOnlyTestItems, TestInfrastructure testInfrastructure, String caller,
                                      RequestFormatter requestFormatter, ResponseFormatter responseFormatter) {
        this.writeOnlyTestItems = writeOnlyTestItems;
        this.testInfrastructure = testInfrastructure;
        this.caller = caller;
        this.requestFormatter = requestFormatter;
        this.responseFormatter = responseFormatter;
    }

    @Override
    public Request request() {
        Request request = statusRequest(testInfrastructure.serverBaseUrl());
        String formattedRequest = requestFormatter.format(request);
        writeOnlyTestItems.addToCapturedInputsAndOutputs(format("Request from %s to %s", caller, systemName()), formattedRequest);
        return request;
    }

    @Override
    public Response response(Request request) {
        Response response = testInfrastructure.execute(request);
        String formattedResponse;
        try {
            formattedResponse = responseFormatter.format(response);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read response body");
        }
        writeOnlyTestItems.addToCapturedInputsAndOutputs(format("Response from %s to %s", systemName(), caller), formattedResponse);
        return response;
    }

    private Request statusRequest(String baseUrl) {
        HttpUrl statusUrl = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("status")
                .build();

        System.out.println(format("Sending request to '%s'", statusUrl.toString()));

        return new Request.Builder()
                .url(statusUrl)
                .get()
                .build();
    }

    private String systemName() {
        return "BoT Aggregate";
    }
}
