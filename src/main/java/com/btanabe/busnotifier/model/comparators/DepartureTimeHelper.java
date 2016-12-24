package com.btanabe.busnotifier.model.comparators;

import com.btanabe.busnotifier.model.types.ArrivalsAndDepartures;

/**
 * Created by Brian on 12/23/16.
 */
public class DepartureTimeHelper {

    /**
     * This helper function returns the departure time used in all calculations.  If a predicted time is present,
     * it returns that.  If a predicted time is not present, it uses the scheduled departure time.  This time may
     * not be the only one disabled.
     *
     * @param arrivalsAndDepartures
     * @return
     */
    public static Long getDepartureTime(ArrivalsAndDepartures arrivalsAndDepartures) {
        return arrivalsAndDepartures.getPredictedDepartureTime() == 0L ? arrivalsAndDepartures.getScheduledDepartureTime() : arrivalsAndDepartures.getPredictedDepartureTime();
    }
}
