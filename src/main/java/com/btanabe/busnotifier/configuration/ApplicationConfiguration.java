package com.btanabe.busnotifier.configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Created by Brian on 12/29/16.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ApplicationConfiguration {

    @NonNull
    private final Integer updateFrequencyInSeconds;
}
