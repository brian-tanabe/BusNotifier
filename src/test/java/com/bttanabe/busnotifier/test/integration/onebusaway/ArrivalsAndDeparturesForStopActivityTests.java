package com.bttanabe.busnotifier.test.integration.onebusaway;

import com.btanabe.busnotifier.exceptions.InvalidKeyProviderException;
import com.btanabe.busnotifier.model.ArrivalsAndDeparturesForStopModel;
import com.btanabe.busnotifier.onebusaway.ArrivalsAndDeparturesForStopActivity;
import com.bttanabe.busnotifier.test.integration.MockWebRequestBase;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_1_12340;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 11/27/16.
 */
public class ArrivalsAndDeparturesForStopActivityTests extends MockWebRequestBase {

    @Autowired
    @Qualifier("expectedArrivalsAndDeparturesForStop1_12378Model")
    private ArrivalsAndDeparturesForStopModel expectedArrivalsAndDeparturesForStop;


    @Autowired
    @Qualifier("testArrivalsAndDeparturesForStopActivity")
    private ArrivalsAndDeparturesForStopActivity activity;

    @Ignore
    @Test(expected = InvalidKeyProviderException.class)
    public void shouldThrowInvalidKeyProviderExceptionsWhenTheActivityHasAnInvalidApiKey() {

    }

    @Test
    public void shouldBeAbleToGetArrivalsAndDeparturesForStop() throws Throwable {
        ArrivalsAndDeparturesForStopModel model = (ArrivalsAndDeparturesForStopModel) activity.getArrivalsAndDeparturesForStop(STOP_1_12340);
        assertThat(model, is(equalTo(expectedArrivalsAndDeparturesForStop)));
    }
}
