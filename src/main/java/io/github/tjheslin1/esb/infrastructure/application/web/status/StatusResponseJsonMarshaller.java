package io.github.tjheslin1.esb.infrastructure.application.web.status;

import io.github.tjheslin1.esb.application.web.JsonMarshaller;
import io.github.tjheslin1.esb.domain.status.ProbeResult;

import java.util.stream.Stream;

public class StatusResponseJsonMarshaller implements JsonMarshaller<Stream<ProbeResult>> {

    @Override
    public String marshall(Stream<ProbeResult> probeResults) {
        StringBuilder responseBody = new StringBuilder();

        probeResults.forEach(probeResult -> responseBody.append(probeResult.toJson()));

        return responseBody.toString();
    }
}
