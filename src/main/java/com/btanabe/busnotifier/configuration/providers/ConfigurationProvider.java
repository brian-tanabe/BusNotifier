package com.btanabe.busnotifier.configuration.providers;

import com.btanabe.busnotifier.configuration.ApplicationConfiguration;
import com.btanabe.busnotifier.configuration.TravelWindow;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Brian on 12/26/16.
 */
public interface ConfigurationProvider {
    List<TravelWindow> getTravelWindowsToMonitor() throws Exception;

    ApplicationConfiguration getApplicationConfiguration() throws Exception;

    static ConfigurationProvider getDefaultConfigurationProvider() {

        return new ConfigurationProvider() {

            @Override
            public List<TravelWindow> getTravelWindowsToMonitor() throws Exception {
                return new ArrayList<>();
            }

            @Override
            public ApplicationConfiguration getApplicationConfiguration() throws Exception {
                return new ApplicationConfiguration(60, SECONDS);
            }
        };
    }
}
