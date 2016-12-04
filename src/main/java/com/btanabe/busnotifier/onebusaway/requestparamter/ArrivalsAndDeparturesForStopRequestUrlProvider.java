package com.btanabe.busnotifier.onebusaway.requestparamter;

import com.btanabe.busnotifier.secrets.KeyProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by Brian on 12/2/16.
 */
@RequiredArgsConstructor
public class ArrivalsAndDeparturesForStopRequestUrlProvider implements OneBusAwayRequestUrlProvider {
    private static final String API_NAME = "arrivals-and-departures-for-stop";

    @NonNull
    private final String stopId;

    @Override
    public String getRequestUrl(KeyProvider keyProvider) {
        return String.format("%s/%s/%s.json?key=%s", URL_PREFIX, API_NAME, stopId, keyProvider.getApiKey());
    }
}
