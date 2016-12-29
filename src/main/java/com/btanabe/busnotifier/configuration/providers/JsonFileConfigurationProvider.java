package com.btanabe.busnotifier.configuration.providers;

import com.btanabe.busnotifier.configuration.TravelWindow;
import com.btanabe.busnotifier.factories.JsonDeserializer;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Created by Brian on 12/26/16.
 */
@RequiredArgsConstructor
public class JsonFileConfigurationProvider implements ConfigurationProvider {

    @NonNull
    private final File configurationFile;

    @Override
    public List<TravelWindow> getTravelWindowsToMonitor() throws Exception {
        return JsonDeserializer.deserializeResponse(FileUtils.readFileToString(configurationFile), new TypeReference<List<TravelWindow>>() {});
    }
}
