package com.btanabe.busnotifier.factories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Brian on 11/26/16.
 */
public class JsonDeserializer {
    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T deserializeResponse(String serializedResponse, Class<T> outputClazz) throws IOException {
        return mapper.readValue(serializedResponse, outputClazz);
    }

    public static <T> T deserializeResponse(String serializedResponse, TypeReference<T> type) throws IOException {
        return mapper.readValue(serializedResponse, type);
    }
}
