package com.bttanabe.busnotifier.test.integration.onebusaway.call;

import com.btanabe.busnotifier.exceptions.InternalServerException;
import com.btanabe.busnotifier.exceptions.InvalidRequestException;
import com.btanabe.busnotifier.exceptions.ResourceNotFoundException;
import com.btanabe.busnotifier.exceptions.ThrottledRequestException;
import com.btanabe.busnotifier.model.ArrivalsAndDeparturesForStopModel;
import com.btanabe.busnotifier.onebusaway.requestparamter.ArrivalsAndDeparturesForStopRequestUrlProvider;
import com.btanabe.busnotifier.onebusaway.tasks.OneBusAwayWebTask;
import com.btanabe.busnotifier.secrets.KeyProvider;
import com.bttanabe.busnotifier.test.integration.MockWebRequestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_INTERNAL_SEVER_ERROR;
import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_INVALID_KEY;
import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_INVALID_REQUEST;
import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_RESOURCE_NOT_FOUND;
import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_THROTTLED;

/**
 * Created by Brian on 12/5/16.
 */
public class OneBusAwayWebTaskErrorHandlingTests extends MockWebRequestBase {

    @Autowired
    @Qualifier("testOneBusAwayKeyProvider")
    private KeyProvider keyProvider;

    @Test(expected = InvalidRequestException.class)
    public void shouldThrowInvalidRequestExceptionWHenErrorCode400IsReturned() throws Exception {
        makeTestCall(STOP_INVALID_REQUEST);
    }

    @Test(expected = ThrottledRequestException.class)
    public void shouldThrowThrottledRequestExceptionWhenErrorCode401IsReturned() throws Exception {
        makeTestCall(STOP_INVALID_KEY);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowResourceNotFoundExceptionWhenErrorCode404IsReturned() throws Exception {
        makeTestCall(STOP_RESOURCE_NOT_FOUND);
    }

    @Test(expected = ThrottledRequestException.class)
    public void shouldThrowThrottledRequestExceptionWhenErrorCode429IsReturned() throws Exception {
        makeTestCall(STOP_THROTTLED);
    }

    @Test(expected = InternalServerException.class)
    public void shouldThrowInternalServerExceptionWhenErrorCode500IsReturned() throws Exception {
        makeTestCall(STOP_INTERNAL_SEVER_ERROR);
    }

    private void makeTestCall(String stopId) throws Exception {
        OneBusAwayWebTask<ArrivalsAndDeparturesForStopModel> task = new OneBusAwayWebTask<>(keyProvider, new ArrivalsAndDeparturesForStopRequestUrlProvider(stopId), ArrivalsAndDeparturesForStopModel.class);
        task.call();
    }
}
