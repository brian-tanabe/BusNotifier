package com.btanabe.busnotifier.configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Created by Brian on 12/11/16.
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RouteAtStopToMonitor {

    @NonNull
    private final String routeId;

    @NonNull
    private final String stopId;

    @NonNull
    private final String stopName;
}
