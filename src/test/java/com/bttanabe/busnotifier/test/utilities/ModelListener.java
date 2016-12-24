package com.bttanabe.busnotifier.test.utilities;

import com.btanabe.busnotifier.message.internal.BusArrivalMessage;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 12/12/16.
 */
@Getter
public class ModelListener {
    private List<BusArrivalMessage> arrivalMessages = new ArrayList<>();

    @Subscribe
    public void publishedBusArrivalMessage(final BusArrivalMessage arrivalMessage) {
        arrivalMessages.add(arrivalMessage);
    }
}
