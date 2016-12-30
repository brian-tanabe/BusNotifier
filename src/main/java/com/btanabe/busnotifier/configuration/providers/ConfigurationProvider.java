package com.btanabe.busnotifier.configuration.providers;

import com.btanabe.busnotifier.configuration.ApplicationConfiguration;
import com.btanabe.busnotifier.configuration.TravelWindow;

import java.util.ArrayList;
import java.util.List;

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
                return new ApplicationConfiguration(60);
            }
        };
    }
}
