package com.bttanabe.busnotifier.test.integration;

import com.btanabe.busnotifier.exceptions.InvalidKeyProviderException;
import com.btanabe.busnotifier.model.ArrivalsAndDeparturesForStopModel;
import com.btanabe.busnotifier.onebusaway.scheduleprovider.ArrivalsAndDeparturesForStopActivity;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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
    public void shouldBeAbleToGetArrivalsAndDeparturesForStop() throws Exception {
        ArrivalsAndDeparturesForStopModel model = activity.getArrivalsAndDeparturesForStop("1_12340");
        assertThat(model, is(equalTo(expectedArrivalsAndDeparturesForStop)));
    }
}
