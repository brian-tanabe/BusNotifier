package com.btanabe.busnotifier.model.comparators;

import com.btanabe.busnotifier.model.types.ArrivalsAndDepartures;

import java.util.Comparator;

/**
 * Created by Brian on 12/12/16.
 */
public class ArrivalsAndDeparturesComparator implements Comparator<ArrivalsAndDepartures> {

    @Override
    public int compare(ArrivalsAndDepartures lhs, ArrivalsAndDepartures rhs) {

        Long lhsDepartureTime = lhs.getPredictedDepartureTime() == 0L ? lhs.getScheduledDepartureTime() : lhs.getPredictedDepartureTime();
        Long rhsDepartureTime = rhs.getPredictedDepartureTime() == 0L ? rhs.getScheduledDepartureTime() : rhs.getPredictedDepartureTime();

        return lhsDepartureTime.compareTo(rhsDepartureTime);
    }
}
