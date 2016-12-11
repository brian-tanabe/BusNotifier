package com.btanabe.busnotifier.onebusaway.call;

/**
 * Created by Brian on 12/4/16.
 */
public enum ResponseCodes {
    SUCCESS(200),
    INVALID_REQUEST(400),
    THROTTLED_REQUEST(401),
    RESOURCE_NOT_FOUND(404),
    THROTTLED_REQUEST_V2(429),
    SERVER_ERROR(500);

    private Integer responseCode;

    ResponseCodes(int responseCode) {
        this.responseCode = responseCode;
    }

    public Integer getResponseCode() {
        return responseCode;
    }
}
