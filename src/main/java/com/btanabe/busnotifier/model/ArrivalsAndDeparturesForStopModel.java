package com.btanabe.busnotifier.model;

import com.btanabe.busnotifier.model.data.ArrivalsAndDeparturesForStopData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Brian on 11/26/16.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ArrivalsAndDeparturesForStopModel extends Model {
    private ArrivalsAndDeparturesForStopData data;
}
