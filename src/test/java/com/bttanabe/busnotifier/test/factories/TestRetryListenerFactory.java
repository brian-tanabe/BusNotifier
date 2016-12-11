package com.bttanabe.busnotifier.test.factories;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import lombok.Getter;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by Brian on 12/8/16.
 */
public class TestRetryListenerFactory implements FactoryBean<TestRetryListenerFactory.TestRetryListener> {

    @Override
    public TestRetryListener getObject() throws Exception {

        return new TestRetryListener();
    }

    @Override
    public Class<?> getObjectType() {
        return TestRetryListener.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public class TestRetryListener implements RetryListener {

        @Getter
        private long retryCount = 0;

        @Override
        public <V> void onRetry(Attempt<V> attempt) {
            retryCount = attempt.getAttemptNumber();
        }

        public void resetCount() {
            retryCount = 0;
        }
    }
}
