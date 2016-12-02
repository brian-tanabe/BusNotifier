package com.btanabe.busnotifier.model;

import lombok.Data;

/**
 * Created by Brian on 11/26/16.
 */
@Data
public class Model {
    private Integer code;
    private Long currentTime;
    private String text;
    private Integer version;
}
