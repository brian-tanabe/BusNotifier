package com.btanabe.busnotifier.model.types;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class ArrivalsAndDeparturesForStopDataReferences {
    private ArrayList<Agency> agencies;
    private ArrayList<Route> routes;
    private ArrayList<ServiceAlert> situations;
    private ArrayList<Stop> stops;
    private ArrayList<Trip> trips;
}
