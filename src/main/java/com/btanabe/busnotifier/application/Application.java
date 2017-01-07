package com.btanabe.busnotifier.application;

import com.btanabe.busnotifier.configuration.providers.ConfigurationProvider;
import com.btanabe.busnotifier.configuration.providers.JsonFileConfigurationProvider;
import com.btanabe.busnotifier.notifiers.AbstractNotifier;
import com.btanabe.busnotifier.notifiers.GrowlNotifier;
import com.btanabe.busnotifier.onebusaway.ArrivalsAndDeparturesForStopActivity;
import com.btanabe.busnotifier.tasks.MessageFactoryTask;
import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.btanabe.busnotifier.application.Version.getVersion;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * Created by Brian on 12/29/16.
 */
@Slf4j
public class Application {

    private ConfigurationProvider configurationProvider;

    private AsyncEventBus eventBus;
    private ListeningScheduledExecutorService scheduledExecutorService;

    private ArrivalsAndDeparturesForStopActivity arrivalsAndDeparturesForStopActivity;
    private UpdateController updateController;

    private List<AbstractNotifier> notifierList = new ArrayList<>();


    public Application(String[] arguments) throws Exception {
        logApplicationStartup();

        configurationProvider = parseCommandLineArguments(arguments);

        loadDependencies();
    }

    public void startApplication() throws Exception {
        updateController.startUpdateHeartbeat();

        while (!scheduledExecutorService.isShutdown()) {
            Thread.sleep(10);
        }

        logApplicationShutdown();
    }

    private ConfigurationProvider parseCommandLineArguments(String[] arguments) {
        try {
            ConfigurationProvider configurationProvider = CommandLineParser.parse(JsonFileConfigurationProvider.class, arguments, OptionStyle.SIMPLE);
            log.info(String.format("Command line argument configuration: arguments=%s, ApplicationConfiguration=[%s], TravelWindows=%s", Arrays.stream(arguments).collect(toList()), configurationProvider.getApplicationConfiguration(), configurationProvider.getTravelWindowsToMonitor()));
            return configurationProvider;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException exception) {
            log.error(String.format("Unable to read command line parameters.  Stack trace: %s", ExceptionUtils.getStackTrace(exception)));
            return createAndLogDefaultConfiguration();
        } catch (Exception exception) {
            log.error(String.format("Unable to marshal response.  Stack trace: %s", ExceptionUtils.getStackTrace(exception)));
            return createAndLogDefaultConfiguration();
        }
    }

    private ConfigurationProvider createAndLogDefaultConfiguration() {
        ConfigurationProvider defaultConfigurationProvider = ConfigurationProvider.getDefaultConfigurationProvider();
        log.error("Using default configurations.  No TravelWindows, update every 60 seconds.");
        return defaultConfigurationProvider;
    }

    private void loadDependencies() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("./spring-configuration/application-configuration.xml");

        eventBus = (AsyncEventBus) requireNonNull(context.getBean("notificationEventBus"), "notificationEventBus");
        scheduledExecutorService = (ListeningScheduledExecutorService) requireNonNull(context.getBean("listeningScheduledExecutorService"), "listeningScheduledExecutorService");
        arrivalsAndDeparturesForStopActivity = (ArrivalsAndDeparturesForStopActivity) requireNonNull(context.getBean("arrivalsAndDeparturesForStopActivity"), "arrivalsAndDeparturesForStopActivity");

        GrowlNotifier growlNotifier = (GrowlNotifier) context.getBean("growlNotifier");
        eventBus.register(growlNotifier);
        notifierList.add(growlNotifier);

        updateController = createUpdateController(configurationProvider);
    }

    private UpdateController createUpdateController(ConfigurationProvider configurationProvider) throws Exception {
        requireNonNull(eventBus, "eventBus");
        requireNonNull(arrivalsAndDeparturesForStopActivity, "arrivalsAndDeparturesForStopActivity");
        requireNonNull(configurationProvider, "configurationProvider");
        requireNonNull(configurationProvider.getTravelWindowsToMonitor(), "travelWindowList");

        MessageFactoryTask messageFactoryTask = new MessageFactoryTask(eventBus, arrivalsAndDeparturesForStopActivity, configurationProvider.getTravelWindowsToMonitor());
        return new UpdateController(configurationProvider, scheduledExecutorService, eventBus, messageFactoryTask);
    }

    private void logApplicationStartup() {
        log.info(String.format("Starting application.  Version: %s", getVersion()));
    }

    private void logApplicationShutdown() {
        log.info("Application shutting down");
    }

    public static void main(String[] arguments) throws Exception {
        Application application = new Application(arguments);
        application.startApplication();
    }
}
