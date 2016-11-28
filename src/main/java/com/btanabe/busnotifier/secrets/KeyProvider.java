package com.btanabe.busnotifier.secrets;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Base64;

/**
 * Created by Brian on 11/19/16.
 */
@RequiredArgsConstructor
public class KeyProvider {

    @NonNull
    private final String encodedKey;

    public String getApiKey() {
        return new String(Base64.getDecoder().decode(encodedKey));
    }
}
