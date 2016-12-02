package com.btanabe.busnotifier.model;

import com.btanabe.busnotifier.model.data.ArrivalsAndDeparturesForStopData;
import lombok.Data;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class ArrivalsAndDeparturesForStopModel extends Model {
    private ArrivalsAndDeparturesForStopData data;
}
