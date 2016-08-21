package io.github.tjheslin1.acceptance.whens;

import io.github.theangrydev.yatspecfluent.When;
import io.github.theangrydev.yatspecfluent.WriteOnlyTestItems;
import io.github.tjheslin1.acceptance.TestInfrastructure;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.String.format;

public class WhenTheStatusPageIsChecked implements When<Request, Response> {

    private final WriteOnlyTestItems writeOnlyTestItems;
    private final TestInfrastructure testInfrastructure;
    private final String caller;

    public WhenTheStatusPageIsChecked(WriteOnlyTestItems writeOnlyTestItems, TestInfrastructure testInfrastructure, String caller) {
        this.writeOnlyTestItems = writeOnlyTestItems;
        this.testInfrastructure = testInfrastructure;
        this.caller = caller;
    }

    @Override
    public Request request() {
        Request request = statusRequest(testInfrastructure.serverBaseUrl());
        writeOnlyTestItems.addToCapturedInputsAndOutputs(format("Request from %s to %s", caller, systemName()), request);
        return request;
    }

    @Override
    public Response response(Request request) {
        Response response = testInfrastructure.execute(request);
        writeOnlyTestItems.addToCapturedInputsAndOutputs(format("Response from %s to %s", systemName(), caller), response);
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
