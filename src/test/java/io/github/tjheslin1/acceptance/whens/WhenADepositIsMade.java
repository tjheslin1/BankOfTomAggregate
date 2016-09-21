package io.github.tjheslin1.acceptance.whens;

import io.github.theangrydev.yatspecfluent.When;
import io.github.theangrydev.yatspecfluent.WriteOnlyTestItems;
import io.github.tjheslin1.acceptance.RequestFormatter;
import io.github.tjheslin1.acceptance.ResponseFormatter;
import io.github.tjheslin1.acceptance.TestInfrastructure;
import okhttp3.*;

import java.io.IOException;

import static java.lang.String.format;

public class WhenADepositIsMade implements When<Request, Response> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final WriteOnlyTestItems writeOnlyTestItems;
    private final TestInfrastructure testInfrastructure;
    private final String caller;
    private RequestFormatter requestFormatter;
    private ResponseFormatter responseFormatter;

    private int accountId;
    private double amount;

    public WhenADepositIsMade(WriteOnlyTestItems writeOnlyTestItems, TestInfrastructure testInfrastructure, String caller,
                              RequestFormatter requestFormatter, ResponseFormatter responseFormatter) {
        this.writeOnlyTestItems = writeOnlyTestItems;
        this.testInfrastructure = testInfrastructure;
        this.caller = caller;
        this.requestFormatter = requestFormatter;
        this.responseFormatter = responseFormatter;
    }

    @Override
    public Request request() {
        Request request = depositRequest(testInfrastructure.serverBaseUrl(), accountId, amount);
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

    public WhenADepositIsMade forAccountWithId(int accountId) {
        this.accountId = accountId;
        return this;
    }

    public WhenADepositIsMade withAmount(double amount) {
        this.amount = amount;
        return this;
    }

    private Request depositRequest(String baseUrl, int accountId, double amount) {
        HttpUrl depositUrl = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("deposit")
                .build();

        String json = format("{ \"accountId\": \"%s\", \"amount\": \"%s\"}", accountId, amount);

        System.out.println(format("Sending request to '%s' with body '%s'", depositUrl.toString(), json));

        return new Request.Builder()
                .url(depositUrl)
                .post(RequestBody.create(JSON, json))
                .build();
    }

    private String systemName() {
        return "BoT Aggregate";
    }
}
