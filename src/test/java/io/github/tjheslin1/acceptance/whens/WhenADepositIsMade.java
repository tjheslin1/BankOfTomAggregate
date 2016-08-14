package io.github.tjheslin1.acceptance.whens;

import io.github.tjheslin1.acceptance.TestInfrastructure;
import io.github.theangrydev.yatspecfluent.When;
import io.github.theangrydev.yatspecfluent.WriteOnlyTestItems;
import okhttp3.*;

import static java.lang.String.format;

public class WhenADepositIsMade implements When<Request, Response> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final WriteOnlyTestItems writeOnlyTestItems;
    private final TestInfrastructure testInfrastructure;
    private final String caller;

    private int accountId;
    private double amount;

    public WhenADepositIsMade(WriteOnlyTestItems writeOnlyTestItems, TestInfrastructure testInfrastructure, String caller) {
        this.writeOnlyTestItems = writeOnlyTestItems;
        this.testInfrastructure = testInfrastructure;
        this.caller = caller;
    }

    @Override
    public Request request() {
        Request request = depositRequest(testInfrastructure.serverBaseUrl(), accountId, amount);
        writeOnlyTestItems.addToCapturedInputsAndOutputs(format("Request from %s to %s", caller, systemName()), request);
        return request;
    }

    @Override
    public Response response(Request request) {
        Response response = testInfrastructure.execute(request);
        writeOnlyTestItems.addToCapturedInputsAndOutputs(format("Response from %s to %s", systemName(), caller), response);
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
