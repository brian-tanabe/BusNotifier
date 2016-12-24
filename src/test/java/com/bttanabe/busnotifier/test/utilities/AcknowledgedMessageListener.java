package com.bttanabe.busnotifier.test.utilities;

import com.btanabe.busnotifier.message.internal.AcknowledgedMessage;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 12/23/16.
 */
@Getter
public class AcknowledgedMessageListener {
    private List<AcknowledgedMessage> acknowledgedMessages = new ArrayList<>();

    @Subscribe
    public void publishedAcknowledgeMessage(final AcknowledgedMessage acknowledgedMessage) {
        acknowledgedMessages.add(acknowledgedMessage);
    }
}
