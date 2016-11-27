package com.btanabe.busnotifier.model.types;

import lombok.Data;

import java.util.List;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class TripStatus {
    private String activeTripId;
    private Trip activeTrip;
    private Integer blockTripSequence;
    private Long serviceDate;
    private Frequency frequency;
    private Double scheduledDistanceAlongTrip;
    private Double totalDistanceAlongTrip;
    private String phase;
    private String status;
    private Double distanceAlongTrip;
    private Location position;
    private Double orientation;
    private String closestStop;
    private Integer closestStopTimeOffset;
    private String nextStop;
    private Integer nextStopTimeOffset;
    private Double nextStopDistanceFromVehicle;
    private String previousStop;
    private Integer previousStopTimeOffset;
    private Double previousStopDistanceFromVehicle;
    private Boolean predicted;
    private Long lastUpdateTime;
    private Long lastLocationUpdateTime;
    private Double lastKnownDistanceAlongTrip;
    private Location lastKnownLocation;
    private Double lastKnownOrientation;
    private Double scheduleDeviation;
    private String vehicleId;
    private List<ServiceAlert> situationIds;
//    private List<TimepointPrediction> timepointPredictions;
}
