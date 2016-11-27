package com.btanabe.busnotifier.factories;

import com.btanabe.busnotifier.model.Model;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Brian on 11/26/16.
 */
public class JsonSerializer {
    private static ObjectMapper mapper = new ObjectMapper();

    public static <T extends Model> T serializeResponse(String unserializedResponse, Class<T> outputClazz) throws IOException {
        return mapper.readValue(unserializedResponse, outputClazz);
    }
}
