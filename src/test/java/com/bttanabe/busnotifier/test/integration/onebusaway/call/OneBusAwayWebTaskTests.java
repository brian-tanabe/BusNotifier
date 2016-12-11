package com.bttanabe.busnotifier.test.integration.onebusaway.call;

import com.btanabe.busnotifier.model.ArrivalsAndDeparturesForStopModel;
import com.btanabe.busnotifier.onebusaway.requestparamter.ArrivalsAndDeparturesForStopRequestUrlProvider;
import com.btanabe.busnotifier.onebusaway.tasks.OneBusAwayWebTask;
import com.btanabe.busnotifier.secrets.KeyProvider;
import com.bttanabe.busnotifier.test.integration.MockWebRequestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_1_12340;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/5/16.
 */
public class OneBusAwayWebTaskTests extends MockWebRequestBase {

    @Autowired
    @Qualifier("testOneBusAwayKeyProvider")
    private KeyProvider keyProvider;

    @Autowired
    @Qualifier("expectedArrivalsAndDeparturesForStop1_12378Model")
    private ArrivalsAndDeparturesForStopModel expectedArrivalsAndDeparturesForStopModel;

    @Test
    public void shouldBeAbleToDownloadAndSerializeArrivalsAndDeparturesForStopApiCalls() throws Exception {
        OneBusAwayWebTask<ArrivalsAndDeparturesForStopModel> task = new OneBusAwayWebTask<>(keyProvider, new ArrivalsAndDeparturesForStopRequestUrlProvider(STOP_1_12340), ArrivalsAndDeparturesForStopModel.class);
        assertThat(task.call(), is(equalTo(expectedArrivalsAndDeparturesForStopModel)));
    }
}
