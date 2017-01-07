package com.btanabe.busnotifier.onebusaway.listeners;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Created by Brian on 12/11/16.
 */
@Slf4j
public class LoggingRetryListener implements RetryListener {

    @Override
    public <V> void onRetry(Attempt<V> attempt) {
        if (attempt.hasResult()) {
            logSuccessAttemptMessage(attempt);
        } else {
            logUnsuccessfulAttemptMessage(attempt);
        }
    }

    private <V> void logSuccessAttemptMessage(Attempt<V> attempt) {
        log.info(String.format("Successful request: retries needed=[%d], time since first attempt=[%d ms]", attempt.getAttemptNumber() - 1, attempt.getDelaySinceFirstAttempt()));
    }

    /**
     * TODO FIGURE OUT IF ITEMS WITHOUT RESULTS WILL ALWAYS HAVE AN EXCEPTION.  I'M NOT SURE IF THEY WILL.
     *
     * @param attempt
     * @param <V>
     */
    private <V> void logUnsuccessfulAttemptMessage(Attempt<V> attempt) {
        log.error(String.format("Unsuccessful request: attempt count=[%d], time since first attempt=[%d ms], cause=[%s]", attempt.getAttemptNumber(), attempt.getDelaySinceFirstAttempt(), ExceptionUtils.getRootCause(attempt.getExceptionCause()).getClass().getSimpleName()));
    }
}
