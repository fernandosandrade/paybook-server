package org.paybook.com;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.google.cloud.Timestamp;

import java.io.IOException;
import java.time.Instant;

public class GoogleTimestampAdapter {

    /**
     * Deserialize {@link Timestamp} to {@link Instant}
     */
    public static class Deserializer extends JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonParser p,
                                   DeserializationContext ctxt) throws IOException {
            var node = p.getCodec().readTree(p);
            return Instant.ofEpochSecond(((LongNode) node.get("seconds")).longValue(),
                    ((IntNode) node.get("nanos")).longValue());
        }
    }

}
