package com.btanabe.busnotifier.utilities;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Brian on 1/29/17.
 */
@Slf4j
public class QueuingRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        log.error(String.format("Rejected execution: pool size = %d, active threads = %d, queued tasks = %d, completed tasks = %d",
                executor.getCorePoolSize(),
                executor.getActiveCount(),
                executor.getQueue().size(),
                executor.getCompletedTaskCount()
        ));
        log.error(String.format("Queuing rejected task: %s", r.toString()));

        try {
            executor.getQueue().put(r);
        } catch (InterruptedException e) {
            log.error(String.format("Failed to queue the rejected task: %s due to exception=[%s], stacktrace: ", r, e.getClass().getName(), ExceptionUtils.getStackTrace(e)));
            throw new RuntimeException(e);
        }
    }
}
