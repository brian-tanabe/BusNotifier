package com.btanabe.busnotifier.utilities;

import com.btanabe.busnotifier.configuration.ApplicationConfiguration;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Brian on 1/2/17.
 */
public class ApplicationConfigurationJsonDeserializer extends StdDeserializer<ApplicationConfiguration> {

    protected ApplicationConfigurationJsonDeserializer() {
        this(null);
    }

    protected ApplicationConfigurationJsonDeserializer(Class<ApplicationConfiguration> item) {
        super(item);
    }

    @Override
    public ApplicationConfiguration deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Integer updateFrequencyInSeconds = node.get(ApplicationConfiguration.UPDATE_FREQUENCY).asInt();
        TimeUnit updateFrequencyTimeUnit = getUpdateFrequencyTimeUnit(node);

        return new ApplicationConfiguration(updateFrequencyInSeconds, updateFrequencyTimeUnit);
    }

    private TimeUnit getUpdateFrequencyTimeUnit(JsonNode node) {
        String updateFrequencyTimeUnitString = node.get(ApplicationConfiguration.UPDATE_FREQUENCY_TIME_UNIT).asText();
        return TimeUnit.valueOf(updateFrequencyTimeUnitString);
    }
}
