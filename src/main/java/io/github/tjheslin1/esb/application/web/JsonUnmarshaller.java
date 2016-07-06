package io.github.tjheslin1.esb.application.web;

public interface JsonUnmarshaller<Request> {

    Request unmarshall(String requestBody);
}
