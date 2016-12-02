package com.btanabe.busnotifier.model.data;

import com.btanabe.busnotifier.model.types.References;
import com.btanabe.busnotifier.model.types.Stop;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by Brian on 12/1/16.
 */
@Data
public class StopsForLocationData {
    private Boolean limitExceeded;
    private ArrayList<Stop> list;
    private Boolean outOfRange;
    private References references;
}
