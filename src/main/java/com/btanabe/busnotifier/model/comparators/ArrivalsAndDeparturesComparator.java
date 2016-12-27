package com.btanabe.busnotifier.model.comparators;

import com.btanabe.busnotifier.model.types.ArrivalsAndDepartures;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Created by Brian on 12/12/16.
 * <p>
 */
public class ArrivalsAndDeparturesComparator implements Comparator<ArrivalsAndDepartures> {

    @Override
    public int compare(ArrivalsAndDepartures lhs, ArrivalsAndDepartures rhs) {

        LocalDateTime lhsDepartureTime = DepartureTimeHelper.getDepartureLocalDateTime(lhs);
        LocalDateTime rhsDepartureTime = DepartureTimeHelper.getDepartureLocalDateTime(rhs);

        return lhsDepartureTime.compareTo(rhsDepartureTime);
    }
}
