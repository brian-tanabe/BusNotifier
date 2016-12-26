package com.btanabe.busnotifier.onebusaway.requestparamter;

import com.btanabe.busnotifier.exceptions.MalformedRequestUrlException;
import com.btanabe.busnotifier.secrets.KeyProvider;
import com.google.common.base.Strings;
import lombok.NonNull;

/**
 * Created by Brian on 12/2/16.
 */
public abstract class OneBusAwayRequestUrlProvider {
    private static final String ONE_BUS_AWAY_API_URL_PREFIX = "http://api.pugetsound.onebusaway.org/api/";

    protected static final String URL_PREFIX = "http://api.pugetsound.onebusaway.org/api/where";
    protected static final String RESPONSE_FORMAT = "json";

    public abstract String getRequestUrl(KeyProvider keyProvider);

    protected String validateInputParameters(@NonNull String requestUrl) {
        if (Strings.isNullOrEmpty(requestUrl)) {
            throw new MalformedRequestUrlException();
        }

        if (!requestUrl.startsWith(ONE_BUS_AWAY_API_URL_PREFIX)) {
            throw new MalformedRequestUrlException();
        }

        return requestUrl;
    }
}
