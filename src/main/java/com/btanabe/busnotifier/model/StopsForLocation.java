package com.btanabe.busnotifier.model;

import com.btanabe.busnotifier.model.data.StopsForLocationData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Brian on 12/1/16.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, includeFieldNames = true)
public class StopsForLocation extends Model {
    private StopsForLocationData data;
}
