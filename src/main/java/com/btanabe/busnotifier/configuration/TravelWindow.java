package com.btanabe.busnotifier.configuration;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Brian on 12/22/16.
 */
@Getter
@RequiredArgsConstructor
public class TravelWindow {

    @NonNull
    private final RouteAtStopToMonitor routeAtStopToMonitor;

    @NonNull
    private final LocalTime windowStartTimeInclusive;

    @NonNull
    private final LocalTime windowEndTimeInclusive;

    @NonNull
    private final Long minutesBeforeArrivalToStartNotifying;

    public boolean isTimeWithinWindow(@NonNull LocalDateTime time) {

        /*
         * In order to make the comparisons inclusive, we must make the start and end times one
         * minute less or greater than their specified times:
         */
        LocalDateTime startDateTime = createStartingLocalDateTime();
        LocalDateTime endDateTime = createEndingLocalDateTime();

        return time.isAfter(startDateTime) && time.isBefore(endDateTime);
    }

    public boolean shouldSendNotification(@NonNull Long timeUntilArrival) {
        return timeUntilArrival <= minutesBeforeArrivalToStartNotifying;
    }

    private LocalDateTime createStartingLocalDateTime() {
        return windowStartTimeInclusive.atDate(LocalDate.now()).minusMinutes(1L);
    }

    private LocalDateTime createEndingLocalDateTime() {
        if (windowEndTimeInclusive.isAfter(windowStartTimeInclusive))
            return windowEndTimeInclusive.atDate(LocalDate.now()).plusMinutes(1L);
        else
            return windowEndTimeInclusive.atDate(LocalDate.now()).plusMinutes(1L).plusDays(1L);
    }
}
