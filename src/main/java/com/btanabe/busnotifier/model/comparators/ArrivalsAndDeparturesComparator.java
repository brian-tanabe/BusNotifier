package com.btanabe.busnotifier.model.comparators;

import com.btanabe.busnotifier.model.types.ArrivalsAndDepartures;

import java.util.Comparator;

/**
 * Created by Brian on 12/12/16.
 * <p>
 */
public class ArrivalsAndDeparturesComparator implements Comparator<ArrivalsAndDepartures> {

    @Override
    public int compare(ArrivalsAndDepartures lhs, ArrivalsAndDepartures rhs) {

        Long lhsDepartureTime = DepartureTimeHelper.getDepartureTime(lhs);
        Long rhsDepartureTime = DepartureTimeHelper.getDepartureTime(rhs);

        return lhsDepartureTime.compareTo(rhsDepartureTime);
    }
}
