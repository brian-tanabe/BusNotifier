package com.btanabe.busnotifier.secrets;

import java.util.Base64;

/**
 * Created by Brian on 11/19/16.
 */
public class OneBusAwayKeyProvider implements KeyProvider {

    private static String ENCODED_KEY = "NjNjZGVjYzQtYjA1MS00ODczLWE0MDktZTFjNmRhOWRiNGYw";

    @Override
    public String getApiKey() {
        return new String(Base64.getDecoder().decode(ENCODED_KEY));
    }
}
