package io.github.tjheslin1.acceptance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonPrettyPrinter {

    public static String prettyPrint(String body) {
        if (body == null || body.isEmpty()) {
            return body;
        }

        JsonObject json = new JsonParser().parse(body).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}
