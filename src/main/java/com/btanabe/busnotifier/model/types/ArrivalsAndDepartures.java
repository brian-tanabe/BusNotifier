package com.btanabe.busnotifier.model.types;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by Brian on 11/26/16.
 */
@Data
@ToString
public class ArrivalsAndDepartures {
    private Trip trip;
    private Long serviceDate;
    private String vehicleId;
    private String stopId;
    private Integer stopSequence;
    private Integer blockTripSequence;
    private Boolean arrivalEnabled;
    private Long scheduledArrivalTime;
    private TimeInterval scheduledArrivalInterval;
    private Long predictedArrivalTime;
    private TimeInterval predictedArrivalInterval;
    private Boolean departureEnabled;
    private Long scheduledDepartureTime;
    private TimeInterval scheduledDepartureInterval;
    private Long predictedDepartureTime;
    private TimeInterval predictedDepartureInterval;
    private Frequency frequency;
    private Boolean predicted;
    private Long lastUpdateTime;
    private String status;
    private Double distanceFromStop;
    private Integer numberOfStopsAway;
    private String routeShortName;
    private String routeLongName;
    private String routeId;
    private String tripId;
    private String tripHeadsign;
    private TripStatus tripStatus;
    private Integer totalStopsInTrip;
    private List<ServiceAlert> situationIds;
}
