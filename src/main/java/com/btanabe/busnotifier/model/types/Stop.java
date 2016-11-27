package com.btanabe.busnotifier.model.types;

import lombok.Data;

import java.util.List;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class Stop {
    private String id;
    private Double lat;
    private Double lon;
    private String direction;
    private String name;
    private String code;
    private Integer locationType;
    private List<String> routeIds;
    private EAccessibility wheelchairBoarding;
}
