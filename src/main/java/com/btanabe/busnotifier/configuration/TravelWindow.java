package com.btanabe.busnotifier.configuration;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by Brian on 12/22/16.
 */
@Getter
@RequiredArgsConstructor
public class TravelWindow {

    @NonNull
    private final RouteAtStopToMonitor routeAtStopToMonitor;

    @NonNull
    private final Long windowStartTimeInclusive;

    @NonNull
    private final Long windowEndTimeInclusive;

    @NonNull
    private final Long minutesBeforeArrivalToStartNotifying;

    public boolean isTimeWithinWindow(@NonNull Long time) {
        return windowStartTimeInclusive <= time && time <= windowEndTimeInclusive;
    }

    public boolean shouldSendNotification(@NonNull Long timeUntilArrival) {
        return timeUntilArrival <= minutesBeforeArrivalToStartNotifying;
    }
}
