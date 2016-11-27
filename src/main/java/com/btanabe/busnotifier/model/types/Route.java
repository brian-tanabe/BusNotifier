package com.btanabe.busnotifier.model.types;

import lombok.Data;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class Route {
    private String id;
    private String agencyId;
    private String shortName;
    private String longName;
    private String description;
    private Integer type;
    private String url;
    private String color;
    private String textColor;
}
