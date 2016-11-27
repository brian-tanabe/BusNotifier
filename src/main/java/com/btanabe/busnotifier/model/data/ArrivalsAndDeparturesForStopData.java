package com.btanabe.busnotifier.model.data;

import com.btanabe.busnotifier.model.types.ArrivalsAndDeparturesForStopDataEntry;
import com.btanabe.busnotifier.model.types.ArrivalsAndDeparturesForStopDataReferences;
import lombok.Data;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class ArrivalsAndDeparturesForStopData {
    private ArrivalsAndDeparturesForStopDataEntry entry;
    private ArrivalsAndDeparturesForStopDataReferences references;
}
