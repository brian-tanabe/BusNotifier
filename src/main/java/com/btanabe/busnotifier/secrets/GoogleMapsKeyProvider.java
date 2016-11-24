package com.btanabe.busnotifier.secrets;

import java.util.Base64;

/**
 * Created by Brian on 11/24/16.
 */
public class GoogleMapsKeyProvider implements KeyProvider {
    private static String ENCODED_KEY = "QUl6YVN5RFVaSFJmSVp4empxWU9jd3d4elB2SC0tMzlGaW44NUZr";

    @Override
    public String getApiKey() {
        return new String(Base64.getDecoder().decode(ENCODED_KEY));
    }
}
