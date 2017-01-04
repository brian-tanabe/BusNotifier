package com.btanabe.busnotifier.message.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * Created by Brian on 11/24/16.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShutdownSignal implements InternalMessage {
    private Long waitTimeout;
    private TimeUnit waitTimeoutTimeUnit;
}
