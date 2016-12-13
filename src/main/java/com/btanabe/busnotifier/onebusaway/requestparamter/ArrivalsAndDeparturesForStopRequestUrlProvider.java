package com.btanabe.busnotifier.onebusaway.requestparamter;

import com.btanabe.busnotifier.secrets.KeyProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by Brian on 12/2/16.
 */
@RequiredArgsConstructor
public class ArrivalsAndDeparturesForStopRequestUrlProvider extends OneBusAwayRequestUrlProvider {
    private static final String API_NAME = "arrivals-and-departures-for-stop";

    @NonNull
    private final String stopId;

    @Override
    public String getRequestUrl(@NonNull KeyProvider keyProvider) {
        String url = String.format("%s/%s/%s.%s?key=%s&minutesBefore=0", URL_PREFIX, API_NAME, stopId, RESPONSE_FORMAT, keyProvider.getApiKey());
        return validateInputParameters(url);
    }
}
