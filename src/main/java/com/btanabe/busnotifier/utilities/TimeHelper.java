package com.btanabe.busnotifier.utilities;

import java.util.concurrent.TimeUnit;

/**
 * Created by Brian on 12/23/16.
 * <p>
 * TODO I think there should also be a Presenter class which takes in the output of this class
 * TODO and displays it properly.  That'll help with unit conversion/display and allow me to use
 * TODO "NOW" instead of
 */
public class TimeHelper {

    public static Long getTimeDifferenceInMinutes(long closerTimeInMillis, long fartherTimeInMillis) {
        long timeDifference = fartherTimeInMillis - closerTimeInMillis;

        return TimeUnit.MILLISECONDS.toMinutes(timeDifference);
    }
}
