package io.github.tjheslin1.esb.infrastructure.application.web.deposit;

import io.github.tjheslin1.esb.application.web.JsonUnmarshaller;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.String.format;

public class DepositRequestJsonUnmarshaller implements JsonUnmarshaller<DepositRequest> {

    @Override
    public DepositRequest unmarshall(String requestBody) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(requestBody);

            return new DepositRequest(
                    Integer.parseInt(jsonObject.get("accountId").toString()),
                    Double.parseDouble(jsonObject.get("amount").toString()));
        } catch (JSONException e) {
            throw new JSONException(format("Error unmarshalling DepositRequest with request body: %s", requestBody), e);
        }
    }
}
