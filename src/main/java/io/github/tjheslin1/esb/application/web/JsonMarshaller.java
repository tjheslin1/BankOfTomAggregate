package io.github.tjheslin1.esb.application.web;

public interface JsonMarshaller<T> {

    String marshall(T t);
}
