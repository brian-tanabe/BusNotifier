package com.btanabe.busnotifier.notifiers;

import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.btanabe.busnotifier.notifiers.notifier.NotificationFactory;
import com.btanabe.busnotifier.notifiers.notifier.NotifierFactory;
import com.google.common.eventbus.EventBus;
import fr.jcgay.notification.Notifier;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * Created by Brian on 2/3/17.
 */
@Slf4j
public class GrowlNotifier extends AbstractNotifier {

    @Setter(onMethod = @__({@Autowired, @Qualifier("notifierFactory")}))
    private NotifierFactory notifierFactory;

    public GrowlNotifier(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void postNotification(BusArrivalMessage message) throws Exception {
        log.info("Received notification: " + message);

        Notifier notifier = notifierFactory.createNotifier();
        try {
            notifier.send(NotificationFactory.createNotification(message));
        } finally {
            notifier.close();
        }

        log.info("Notification posted");
    }
}
