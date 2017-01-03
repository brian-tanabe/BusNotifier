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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Brian on 12/29/16.
 */
@NoArgsConstructor
@AllArgsConstructor
public class UpdateController {

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

        scheduledThreadPool.schedule(messageFactoryTask, updateFrequency, updateFrequencyTimeUnit);
    }

    @Subscribe
    public void shutdownThreadPool(ShutdownSignal shutdownSignal) {
        scheduledThreadPool.shutdown();
        eventBus.post(new AcknowledgedMessage(shutdownSignal));
    }
}
