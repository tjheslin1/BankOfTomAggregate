package io.github.tjheslin1.esb.infrastructure.application.web.status;

import io.github.tjheslin1.esb.application.web.JsonMarshaller;
import io.github.tjheslin1.esb.domain.status.ProbeResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatusResponseJsonMarshaller implements JsonMarshaller<List<ProbeResult>> {

//    private static final String STATUS_PAGE_FORMAT = "{}";

    @Override
    public String marshall(List<ProbeResult> probeResults) {
        return probeResults.stream().map(ProbeResult::toJson).collect(Collectors.joining(", "));
    }
}
