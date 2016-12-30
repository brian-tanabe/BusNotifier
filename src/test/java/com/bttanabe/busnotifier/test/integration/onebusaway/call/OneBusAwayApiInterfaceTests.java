package com.bttanabe.busnotifier.test.integration.onebusaway.call;

import com.btanabe.busnotifier.exceptions.InvalidRequestException;
import com.btanabe.busnotifier.exceptions.ResourceNotFoundException;
import com.btanabe.busnotifier.exceptions.RetriesExceededException;
import com.btanabe.busnotifier.model.ArrivalsAndDeparturesForStopModel;
import com.btanabe.busnotifier.onebusaway.requestparamter.ArrivalsAndDeparturesForStopRequestUrlProvider;
import com.bttanabe.busnotifier.test.factories.TestRetryListenerFactory;
import com.bttanabe.busnotifier.test.integration.MockWebRequestBase;
import com.bttanabe.busnotifier.test.utilities.TestableOneBusAwayApiInterface;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_INTERNAL_SEVER_ERROR;
import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_INVALID_KEY;
import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_INVALID_REQUEST;
import static com.bttanabe.busnotifier.test.constants.TestStopIds.STOP_RESOURCE_NOT_FOUND;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Brian on 12/4/16.
 * <p>
 */
public class OneBusAwayApiInterfaceTests extends MockWebRequestBase {

    @Autowired
    @Qualifier("testOneBusAwayApiInterface")
    private TestableOneBusAwayApiInterface apiInterface;

    @Autowired
    @Qualifier("testRetryListener")
    private TestRetryListenerFactory.TestRetryListener retryCounter;

    @Autowired
    @Qualifier("totalAttemptCount")
    private Integer expectedAttemptCount;

    @Before
    public void resetRetryCounter() {
        retryCounter.resetCount();
    }

    @Test
    public void shouldRetryOnThrottledRequestExceptions() throws Throwable {
        assertRetriesOccurred(STOP_INVALID_KEY, RetriesExceededException.class);
    }

    @Test
    public void shouldRetryOnInternalServerException() throws Throwable {
        assertRetriesOccurred(STOP_INTERNAL_SEVER_ERROR, RetriesExceededException.class);
    }

    @Test
    public void shouldNotRetryOnInvalidRequestExceptions() throws Throwable {
        assertRetriesDidNotOccur(STOP_INVALID_REQUEST, InvalidRequestException.class);
    }

    @Test
    public void shouldNotRetryOnResourceNotFoundExceptions() throws Throwable {
        assertRetriesDidNotOccur(STOP_RESOURCE_NOT_FOUND, ResourceNotFoundException.class);
    }

    private void assertRetriesDidNotOccur(String urlKey, Class<?> expectedExceptionClass) throws Throwable {
        try {
            apiInterface.makeApiCall(new ArrivalsAndDeparturesForStopRequestUrlProvider(urlKey), ArrivalsAndDeparturesForStopModel.class);
        } catch (Exception ex) {
            assertThat(ex, is(instanceOf(expectedExceptionClass)));
        } finally {
            assertThat(retryCounter.getRetryCount(), is(equalTo(1L)));
        }
    }

    private void assertRetriesOccurred(String urlKey, Class<?> expectedExceptionClass) throws Throwable {
        try {
            apiInterface.makeApiCall(new ArrivalsAndDeparturesForStopRequestUrlProvider(urlKey), ArrivalsAndDeparturesForStopModel.class);
        } catch (Exception ex) {
            assertThat(ex, is(instanceOf(expectedExceptionClass)));
        } finally {
            assertThat(retryCounter.getRetryCount(), is(equalTo(expectedAttemptCount.longValue())));
        }
    }
}
