package com.btanabe.busnotifier.application;

import com.btanabe.busnotifier.configuration.providers.ConfigurationProvider;
import com.btanabe.busnotifier.message.internal.AcknowledgedMessage;
import com.btanabe.busnotifier.message.internal.ShutdownSignal;
import com.btanabe.busnotifier.tasks.MessageFactoryTask;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Brian on 12/29/16.
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class UpdateController {
    private static final Long DEFAULT_SHUTDOWN_WAIT_TIMEOUT = 1000L;

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private ConfigurationProvider configurationProvider;

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private ScheduledExecutorService scheduledThreadPool;

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private EventBus eventBus;

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private MessageFactoryTask messageFactoryTask;

    public void startUpdateHeartbeat() throws Exception {
        final Integer updateFrequency = configurationProvider.getApplicationConfiguration().getUpdateFrequency();
        final TimeUnit updateFrequencyTimeUnit = configurationProvider.getApplicationConfiguration().getUpdateFrequencyTimeUnit();

        log.info(String.format("Starting update at every %d %s", updateFrequency, updateFrequencyTimeUnit.toString()));

        scheduledThreadPool.scheduleAtFixedRate(messageFactoryTask, 0L, updateFrequency, updateFrequencyTimeUnit);
    }

    @Subscribe
    public void shutdownThreadPool(ShutdownSignal shutdownSignal) throws InterruptedException {
        long waitTimeout = isNull(shutdownSignal.getWaitTimeout()) ? DEFAULT_SHUTDOWN_WAIT_TIMEOUT : shutdownSignal.getWaitTimeout();
        TimeUnit waitTimeoutTimeUnit = isNull(shutdownSignal.getWaitTimeoutTimeUnit()) ? MILLISECONDS : shutdownSignal.getWaitTimeoutTimeUnit();

        log.info(String.format("Shutting down update thread pool.  Will wait a maximum of %d %s for all outstanding requests to finish", waitTimeout, waitTimeoutTimeUnit.toString()));

        shutdownThreadPool(waitTimeout, waitTimeoutTimeUnit);

        eventBus.post(new AcknowledgedMessage(shutdownSignal));
    }

    private void shutdownThreadPool(long timeout, TimeUnit waitTimeout) throws InterruptedException {
        scheduledThreadPool.awaitTermination(timeout, waitTimeout);
    }
}
