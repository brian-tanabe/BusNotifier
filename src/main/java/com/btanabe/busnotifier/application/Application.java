package com.btanabe.busnotifier.application;

import com.btanabe.busnotifier.configuration.providers.ConfigurationProvider;
import com.btanabe.busnotifier.configuration.providers.JsonFileConfigurationProvider;
import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import com.google.common.eventbus.AsyncEventBus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Brian on 12/29/16.
 */
@Slf4j
public class Application {

    private AsyncEventBus eventBus;

    public Application(String[] arguments) {
        logApplicationStartup();

        ConfigurationProvider configurationProvider = parseCommandLineArguments(arguments);

        logApplicationShutdown();
    }

    private ConfigurationProvider parseCommandLineArguments(String[] arguments) {
        try {
            ConfigurationProvider configurationProvider = CommandLineParser.parse(JsonFileConfigurationProvider.class, arguments, OptionStyle.SIMPLE);
            log.info(String.format("Command line argument configuration: ApplicationConfiguration=[%s], TravelWindows=%s", configurationProvider.getApplicationConfiguration(), configurationProvider.getTravelWindowsToMonitor()));
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

//        applicationInfoFactory = (GrowlApplicationInfoFactory) context.getBean("growlApplicationInfoFactory");
//        notificationInfoFactory = (GrowlNotificationInfoFactory) context.getBean("growlNotificationInfoFactory");
//        notificationFactory = (GrowlNotificationFactory) context.getBean("growlNotificationFactory");
//        clientFactory = (GrowlClientFactory) context.getBean("growlClientFactory");

        eventBus = (AsyncEventBus) context.getBean("notificationEventBus");
//        growlNotifier = (GrowlNotifier) context.getBean("growlNotifier");
    }

    private void logApplicationStartup() {
        log.info("Starting application");
    }

    private void logApplicationShutdown() {
        log.info("Application shutting down");
    }

    public static void main(String[] arguments) throws Exception {
        Application application = new Application(arguments);
    }
}
