package com.btanabe.busnotifier.message;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by Brian on 12/11/16.
 */
@Getter
@RequiredArgsConstructor
public class RouteAtStopToMonitor {

    @NonNull
    private final String routeId;

    @NonNull
    private final String stopId;
}
