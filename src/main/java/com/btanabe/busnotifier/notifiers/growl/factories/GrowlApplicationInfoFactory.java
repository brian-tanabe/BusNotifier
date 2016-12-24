package com.btanabe.busnotifier.notifiers.growl.factories;

import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpApplicationInfoBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by Brian on 11/23/16.
 */
@RequiredArgsConstructor
public class GrowlApplicationInfoFactory {

    @NonNull
    private final String applicationName;

    public GntpApplicationInfo createApplicationInfo() {
        GntpApplicationInfo applicationInfo = new GntpApplicationInfoBuilder(applicationName).build();

        return applicationInfo;
    }
}
