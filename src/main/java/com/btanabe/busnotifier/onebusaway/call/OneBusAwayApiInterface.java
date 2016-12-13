package com.btanabe.busnotifier.onebusaway.call;

import com.btanabe.busnotifier.exceptions.RetriesExceededException;
import com.btanabe.busnotifier.model.Model;
import com.btanabe.busnotifier.onebusaway.requestparamter.OneBusAwayRequestUrlProvider;
import com.btanabe.busnotifier.onebusaway.tasks.OneBusAwayWebTask;
import com.btanabe.busnotifier.secrets.KeyProvider;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * Created by Brian on 11/26/16.
 */
@Slf4j
@RequiredArgsConstructor
public class OneBusAwayApiInterface<ApiOutputType extends Model> {

    @NonNull
    private final KeyProvider keyProvider;

    @NonNull
    private final Retryer<ApiOutputType> retryer;

    protected Model makeApiCall(OneBusAwayRequestUrlProvider urlProvider, Class<ApiOutputType> outputClassType) throws Throwable {
        try {
            return retryer.call(new OneBusAwayWebTask(keyProvider, urlProvider, outputClassType));
        } catch (RetryException e) {
            throw new RetriesExceededException(e.getLastFailedAttempt().getExceptionCause());
        } catch (ExecutionException e) {
            throw e.getCause();
        }
    }
}
