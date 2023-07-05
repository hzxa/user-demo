package org.example.user.register.model;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

@JsonSerialize(using = EmptyResponse.Serializer.class)
public class EmptyResponse {
    public static final EmptyResponse INSTANCE = new EmptyResponse();

    private EmptyResponse() {
    }

    /**
     * A custom serializer to make EmptyResponse to be serialized by Jackson.
     * Without this class, Jackson won't serialize EmptyResponse.
     */
    public static class Serializer extends JsonSerializer<EmptyResponse> {
        @Override
        public void serialize(EmptyResponse value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeStartObject();
            gen.writeEndObject();
        }
    }
}
