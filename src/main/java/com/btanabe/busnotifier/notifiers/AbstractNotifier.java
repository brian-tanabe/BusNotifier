package com.btanabe.busnotifier.notifiers;

import com.btanabe.busnotifier.message.internal.AcknowledgedMessage;
import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Brian on 12/23/16.
 */
@Slf4j
@Data
public abstract class AbstractNotifier {

    @NonNull
    protected final EventBus eventBus;

    public AbstractNotifier(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    private void receiveArrivalMessages(BusArrivalMessage message) throws Exception {
        log.info(String.format("notifier=[%s] received message=[%s]", getClass().getSimpleName(), message));

        postNotification(message);
        acknowledgeMessage(message);
    }

    protected abstract void postNotification(BusArrivalMessage message) throws Exception;

    private void acknowledgeMessage(@NonNull BusArrivalMessage message) {
        eventBus.post(new AcknowledgedMessage(message));
    }
}
