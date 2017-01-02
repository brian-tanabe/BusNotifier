package com.btanabe.busnotifier.configuration;

import com.btanabe.busnotifier.utilities.ApplicationConfigurationJsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

/**
 * Created by Brian on 12/29/16.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@JsonDeserialize(using = ApplicationConfigurationJsonDeserializer.class)
public class ApplicationConfiguration {

    public static final String UPDATE_FREQUENCY = "updateFrequency";
    public static final String UPDATE_FREQUENCY_TIME_UNIT = "updateFrequencyTimeUnit";

    @NonNull
    private final Integer updateFrequency;

    @NonNull
    private final TimeUnit updateFrequencyTimeUnit;
}
