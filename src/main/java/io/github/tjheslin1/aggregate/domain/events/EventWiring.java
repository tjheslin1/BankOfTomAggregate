package io.github.tjheslin1.aggregate.domain.events;

import io.github.tjheslin1.aggregate.application.cqrs.command.EventJsonMarshaller;
import io.github.tjheslin1.aggregate.application.cqrs.query.EventJsonUnmarshaller;

public interface EventWiring {
    String collectionName();
    EventJsonMarshaller marshaller();
    EventJsonUnmarshaller unmarshaller();
}
