package com.btanabe.busnotifier.application;

import com.btanabe.busnotifier.notifiers.GrowlNotifier;
import com.btanabe.busnotifier.notifiers.growl.factories.GrowlApplicationInfoFactory;
import com.btanabe.busnotifier.notifiers.growl.factories.GrowlClientFactory;
import com.btanabe.busnotifier.notifiers.growl.factories.GrowlNotificationFactory;
import com.btanabe.busnotifier.notifiers.growl.factories.GrowlNotificationInfoFactory;
import com.btanabe.busnotifier.message.external.GrowlNotification;
import com.btanabe.busnotifier.message.internal.ShutdownSignal;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Brian on 11/19/16.
 */
@Slf4j
public class Sandbox {

    private GrowlNotificationInfoFactory notificationInfoFactory;
    private GrowlApplicationInfoFactory applicationInfoFactory;
    private GrowlNotificationFactory notificationFactory;
    private GrowlClientFactory clientFactory;

    private AsyncEventBus eventBus;
    private GrowlNotifier growlNotifier;

    public static void main(String[] args) throws Exception {
        Sandbox application = new Sandbox();

        application.logApplicationStartup();
        application.loadDependencies();
        application.configureEventBus();
        application.sendNotification();
    }

    private void logApplicationStartup() {
        log.info("Starting application");
    }

    private void logApplicationShutdown() {
        log.info("Application shutting down");
    }

    private void loadDependencies() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("./spring-configuration/application-configuration.xml");

        applicationInfoFactory = (GrowlApplicationInfoFactory) context.getBean("growlApplicationInfoFactory");
        notificationInfoFactory = (GrowlNotificationInfoFactory) context.getBean("growlNotificationInfoFactory");
        notificationFactory = (GrowlNotificationFactory) context.getBean("growlNotificationFactory");
        clientFactory = (GrowlClientFactory) context.getBean("growlClientFactory");

        eventBus = (AsyncEventBus) context.getBean("notificationEventBus");
        growlNotifier = (GrowlNotifier) context.getBean("growlNotifier");
    }

    private void configureEventBus() {
        eventBus.register(growlNotifier);
        eventBus.register(this);
        log.info("message bus registered");
    }

    private void sendNotification() {
        GrowlNotification notification = new GrowlNotification("incoming-bus-route-11-convention-place-east", "Route 11: Convention Place (east)", "14 minutes", null);
        log.info(String.format("posting notification=[%s]", notification));
        eventBus.post(notification);
    }

    @Subscribe
    public void shutdownApplication(ShutdownSignal shutdownSignal) {
        logApplicationShutdown();
        eventBus.unregister(growlNotifier);
        System.exit(0);
    }
}
