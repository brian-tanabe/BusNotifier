package com.bttanabe.busnotifier.test.utilities;

import com.btanabe.busnotifier.message.internal.AcknowledgedMessage;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by Brian on 12/23/16.
 */
@Slf4j
@Getter
public class AcknowledgedMessageListener {
    private List<AcknowledgedMessage> acknowledgedMessages = new ArrayList<>();

    @Subscribe
    public void publishedAcknowledgeMessage(final AcknowledgedMessage acknowledgedMessage) {
        acknowledgedMessages.add(acknowledgedMessage);
    }

    public void blockUntilAcknowledgeMessageIsReceived(@NonNull AcknowledgedMessage expectedAcknowledgeMessage) {

        long waitTimeout = 5000;
        for (long waitStartTime = System.currentTimeMillis(); System.currentTimeMillis() < waitStartTime + waitTimeout; ) {
            if (acknowledgedMessages.contains(expectedAcknowledgeMessage)) {
                return;
            }
        }

        fail(String.format("Timed out after %d milliseconds waiting for Message=[%s].  These messages were received=%s",
                waitTimeout,
                expectedAcknowledgeMessage.getAcknowledgedMessage(),
                acknowledgedMessages));
    }
}
