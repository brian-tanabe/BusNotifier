package com.btanabe.busnotifier.model.types;

import lombok.Data;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class Frequency {
    private Long startTime;
    private Long endTime;
    private Integer headway;
}
