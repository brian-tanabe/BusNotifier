package com.btanabe.busnotifier.utilities;

import lombok.NonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * Created by Brian on 12/23/16.
 * <p>
 * TODO I think there should also be a Presenter class which takes in the output of this class
 * TODO and displays it properly.  That'll help with unit conversion/display and allow me to use
 * TODO "NOW" instead of
 */
public class TimeHelper {

    public static Long getTimeDifferenceInMinutes(LocalDateTime closerTime, LocalDateTime fartherTime) {
        long timeDifference = getTimeEpochTime(fartherTime) - getTimeEpochTime(closerTime);
        return TimeUnit.MILLISECONDS.toMinutes(timeDifference);
    }

    public static Long getTimeEpochTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime getLocalDateTime(@NonNull Long epochTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochTime), ZoneId.systemDefault());
    }
}
