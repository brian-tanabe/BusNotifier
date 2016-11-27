package com.btanabe.busnotifier.model.types;

import lombok.Data;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class Trip {
    private String id;
    private String routeId;
    private String routeShortName;
    private String tripShortName;
    private String tripHeadsign;
    private String serviceId;
    private String shapeId;
    private String timeZone;
    private String directionId;
    private String blockId;
    private Double totalTripDistance;
}
