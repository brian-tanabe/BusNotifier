package com.btanabe.busnotifier.notifiers.growl.factories;

import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpApplicationInfoBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Brian on 11/23/16.
 */
@NoArgsConstructor
@AllArgsConstructor
public class GrowlApplicationInfoFactory {

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private String applicationName;

    public GntpApplicationInfo createApplicationInfo() {
        GntpApplicationInfo applicationInfo = new GntpApplicationInfoBuilder(applicationName).build();

        return applicationInfo;
    }
}
