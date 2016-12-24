package com.btanabe.busnotifier.utilities;

/**
 * Created by Brian on 12/23/16.
 */
public class TimeHelper {

    public static Long getTimeDifferenceInMinutes(long closerTime, long fartherTime) {
        long timeDifference = fartherTime - closerTime;

        // TODO use some helper library:
        long timeInMinutes = timeDifference / 1000 / 60;

        return timeInMinutes;
    }
}
