package com.bttanabe.busnotifier.test.integration.application;

import com.btanabe.busnotifier.application.UpdateController;
import com.bttanabe.busnotifier.test.integration.MockWebRequestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.fail;

/**
 * Created by Brian on 1/2/17.
 */
public class UpdateControllerTests extends MockWebRequestBase {

    @Autowired
    @Qualifier("testUpdateController")
    private UpdateController updateController;

    @Test
    public void shouldBeAbleToPostNotificationForIncomingBuses() throws Exception {
        updateController.startUpdateHeartbeat();

        fail("not yet implemented");
    }
}
