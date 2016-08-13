package io.github.tjheslin1.aggregate.application.web;

public interface JsonUnmarshaller<Request> {

    Request unmarshall(String requestBody);
}
