package com.btanabe.busnotifier.onebusaway.listeners;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import lombok.extern.slf4j.Slf4j;

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
        log.info(String.format("Successful retry attempt: count=[%d], time since first attempt=[%d]", attempt.getAttemptNumber(), attempt.getDelaySinceFirstAttempt()));
    }

    private <V> void logUnsuccessfulAttemptMessage(Attempt<V> attempt) {
        log.error(String.format("Unsuccessful retry attempt: count=[%d], time since first attempt=[%d], cause=[%s]", attempt.getAttemptNumber(), attempt.getDelaySinceFirstAttempt(), attempt.getExceptionCause().getClass().getName()));
    }
}
