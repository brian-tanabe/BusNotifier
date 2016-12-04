package com.btanabe.busnotifier.onebusaway.requestparamter;

import com.btanabe.busnotifier.secrets.KeyProvider;
import lombok.NonNull;

/**
 * Created by Brian on 12/2/16.
 */
public interface OneBusAwayRequestUrlProvider {
    String URL_PREFIX = "http://api.pugetsound.onebusaway.org/api/where";
    String RESPONSE_FORMAT = "json";

    String getRequestUrl(@NonNull KeyProvider keyProvider);
}
