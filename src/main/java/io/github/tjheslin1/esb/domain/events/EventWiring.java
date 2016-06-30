package io.github.tjheslin1.esb.domain.events;

import io.github.tjheslin1.esb.domain.cqrs.command.EventJsonMarshaller;
import io.github.tjheslin1.esb.domain.cqrs.query.EventJsonUnmarshaller;

public interface EventWiring {
    String collectionName();
    EventJsonMarshaller marshaller();
    EventJsonUnmarshaller unmarshaller();
}
