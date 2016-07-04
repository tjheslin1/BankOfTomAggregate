package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.application.cqrs.command.EventJsonMarshaller;
import io.github.tjheslin1.esb.application.cqrs.query.EventJsonUnmarshaller;

public interface EventWiring {
    String collectionName();
    EventJsonMarshaller marshaller();
    EventJsonUnmarshaller unmarshaller();
}
