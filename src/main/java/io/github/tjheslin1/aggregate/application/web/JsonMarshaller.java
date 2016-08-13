package io.github.tjheslin1.aggregate.application.web;

public interface JsonMarshaller<T> {

    String marshall(T t);
}
