package com.btanabe.busnotifier.message.internal;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by Brian on 12/22/16.
 * <p>
 * I'm planning on using this class to communicate between the Task responsible for finding bus schedules
 * and the class responsible for creating external notifications.  This will let me spin up an external
 * notifier implementation on its own thread, from the Application class, register to listen to the
 * EventBus, and create the external notifications their responsible for independently.  This is very
 * losely coupled and will cause little impact when other external notifications tyoes are created.  The
 * first set of external notification creators I think I should write is:
 * <p>
 * 1) Responsible for creating and posting Growl notifications for incoming buses
 * 2) Responsible for creating and posting Growl notifications for debugging purposes
 * <p>
 * Sorry future Brian for stashing this note here
 */
@Data
@Getter
@RequiredArgsConstructor
public class BusArrivalMessage implements InternalMessage {

    @NonNull
    private final Long expectedArrivalTime;

    @NonNull
    private final String routeName;

    @NonNull
    private final String stopLocation;
}
