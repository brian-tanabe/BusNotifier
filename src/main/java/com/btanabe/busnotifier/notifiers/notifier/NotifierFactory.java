package com.btanabe.busnotifier.notifiers.notifier;

import fr.jcgay.notification.Application;
import fr.jcgay.notification.Notifier;
import fr.jcgay.notification.SendNotification;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Brian on 2/5/17.
 */
public class NotifierFactory {

    @Setter(onMethod = @__({@Autowired, @Qualifier("growlApplicationConfiguration")}))
    private Application application;

    public Notifier createNotifier() {
        return new SendNotification().setApplication(application).initNotifier();
    }
}
