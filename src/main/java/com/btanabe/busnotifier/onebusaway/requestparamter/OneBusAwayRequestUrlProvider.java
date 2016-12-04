package com.btanabe.busnotifier.onebusaway.requestparamter;

import com.btanabe.busnotifier.secrets.KeyProvider;

/**
 * Created by Brian on 12/2/16.
 */
public interface OneBusAwayRequestUrlProvider {
    String URL_PREFIX = "http://api.pugetsound.onebusaway.org/api/where";

    String getRequestUrl(KeyProvider keyProvider);
}
