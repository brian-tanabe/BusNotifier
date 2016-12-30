package com.btanabe.busnotifier.configuration.providers;

import com.btanabe.busnotifier.configuration.ApplicationConfiguration;
import com.btanabe.busnotifier.configuration.TravelWindow;
import com.btanabe.busnotifier.utilities.JsonDeserializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.jankroken.commandline.annotations.LongSwitch;
import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.annotations.Required;
import com.github.jankroken.commandline.annotations.ShortSwitch;
import com.github.jankroken.commandline.annotations.SingleArgument;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Created by Brian on 12/26/16.
 */
@NoArgsConstructor
@AllArgsConstructor
public class JsonFileConfigurationProvider implements ConfigurationProvider {
    private File travelWindowConfigurationFile;
    private File applicationConfigurationFile;

    @Override
    public List<TravelWindow> getTravelWindowsToMonitor() throws Exception {
        return JsonDeserializer.deserializeResponse(FileUtils.readFileToString(travelWindowConfigurationFile), new TypeReference<List<TravelWindow>>() {
        });
    }

    @Override
    public ApplicationConfiguration getApplicationConfiguration() throws Exception {
        return JsonDeserializer.deserializeResponse(FileUtils.readFileToString(applicationConfigurationFile), ApplicationConfiguration.class);
    }

    @Option
    @LongSwitch("travelWindowConfigurationFile")
    @ShortSwitch("tc")
    @SingleArgument
    @Required
    public void setTravelWindowConfigurationFile(String fileName) {
        travelWindowConfigurationFile = new File(fileName);
    }

    @Option
    @LongSwitch("applicationConfigurationFile")
    @ShortSwitch("ac")
    @SingleArgument
    @Required
    public void setApplicationConfigurationFile(String fileName) {
        applicationConfigurationFile = new File(fileName);
    }
}
