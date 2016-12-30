package com.btanabe.busnotifier.onebusaway.call;

import com.btanabe.busnotifier.exceptions.RetriesExceededException;
import com.btanabe.busnotifier.model.Model;
import com.btanabe.busnotifier.onebusaway.requestparamter.OneBusAwayRequestUrlProvider;
import com.btanabe.busnotifier.onebusaway.tasks.OneBusAwayWebTask;
import com.btanabe.busnotifier.secrets.KeyProvider;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.ExecutionException;

/**
 * Created by Brian on 11/26/16.
 *
 * TODO Figure out why it cannot autowire the retryer field when it's declared with its proper type,
 * TODO Retryer<ApiOutputType>.
 */
@Slf4j
@AllArgsConstructor
public class OneBusAwayApiInterface<ApiOutputType extends Model> {

    @NonNull
    @Setter(onMethod = @__({@Autowired, @Qualifier("oneBusAwayKeyProvider")}))
    protected KeyProvider keyProvider;

    @NonNull
    @Setter(onMethod = @__({@Autowired, @Qualifier("oneBusAwayRetryStrategy")}))
    protected Object retryer;


    protected Model makeApiCall(OneBusAwayRequestUrlProvider urlProvider, Class<ApiOutputType> outputClassType) throws Throwable {
        try {
            return ((Retryer<ApiOutputType>) retryer).call(new OneBusAwayWebTask(keyProvider, urlProvider, outputClassType));
        } catch (RetryException e) {
            throw new RetriesExceededException(e.getLastFailedAttempt().getExceptionCause());
        } catch (ExecutionException e) {
            throw e.getCause();
        }
    }
}
