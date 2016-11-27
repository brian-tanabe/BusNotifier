package com.btanabe.busnotifier.model.types;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class ArrivalsAndDeparturesForStopDataEntry {
    private String stopId;
    private ArrayList<ArrivalsAndDepartures> arrivalsAndDepartures;
    private ArrayList<String> nearbyStopIds;
    private ArrayList<Object> situationIds;
}
