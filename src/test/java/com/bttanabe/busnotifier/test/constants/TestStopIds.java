package com.bttanabe.busnotifier.test.constants;

/**
 * Created by Brian on 12/6/16.
 */
public class TestStopIds {

    /**
     * Actual stops:
     */
    public static final String STOP_1_12340 = "1_12340";

    /**
     * Fake stops:
     */
    public static final String STOP_INVALID_REQUEST = "invalid-request-me";         // 400
    public static final String STOP_INVALID_KEY = "unauthorize-me";                 // 401
    public static final String STOP_RESOURCE_NOT_FOUND = "resource-not-found-me";   // 404
    public static final String STOP_THROTTLED = "throttle-me";                      // 429
    public static final String STOP_INTERNAL_SEVER_ERROR = "server-error-me";       // 500
}
