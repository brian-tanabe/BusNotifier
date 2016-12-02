package com.btanabe.busnotifier.model;

import com.btanabe.busnotifier.model.data.StopsForLocationData;
import lombok.Data;

/**
 * Created by Brian on 12/1/16.
 */
@Data
public class StopsForLocation extends Model {
    private StopsForLocationData data;
}
