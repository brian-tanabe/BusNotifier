package com.btanabe.busnotifier.factories;

import com.btanabe.busnotifier.exceptions.RetryableException;
import com.btanabe.busnotifier.model.Model;
import com.github.rholder.retry.RetryListener;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Brian on 12/6/16.
 */
public class OneBusAwayRetryStrategyFactory<ApiReturnType extends Model> implements FactoryBean<Retryer<ApiReturnType>> {

    @Setter(onMethod = @__({@Autowired}))
    private Integer baseMultiplier;

    @Setter(onMethod = @__({@Autowired}))
    private Integer totalRequestTimeoutInSeconds;

    @Setter(onMethod = @__({@Autowired}))
    private Integer totalAttemptCount;

    @Setter(onMethod = @__({@Autowired}))
    private RetryListener retryListener;

    @Override
    public Retryer<ApiReturnType> getObject() throws Exception {
        requireNonNull(baseMultiplier);
        requireNonNull(totalRequestTimeoutInSeconds);
        requireNonNull(totalAttemptCount);

        /**
         * A base multiplier of 50 makes the first retry attempt take 100ms, the exact time OneBusAway
         * requests take to cool down on throttle exceptions
         */
        RetryerBuilder<ApiReturnType> retryBuilder = RetryerBuilder.<ApiReturnType>newBuilder()
                .retryIfExceptionOfType(RetryableException.class)
                .withWaitStrategy(WaitStrategies.exponentialWait(baseMultiplier, totalRequestTimeoutInSeconds, SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(totalAttemptCount));

        if (!isNull(retryListener)) {
            retryBuilder.withRetryListener(retryListener);
        }

        return retryBuilder.build();
    }

    @Override
    public Class<?> getObjectType() {
        return RetryerBuilder.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
