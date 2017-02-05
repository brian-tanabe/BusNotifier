package com.btanabe.busnotifier.factories;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Brian on 8/26/16.
 */
@Slf4j
@Setter
public class ThreadPoolFactory {
    private Integer queueCapacity = 10;
    private Integer corePoolSize = 5;
    private Integer maximumPoolSize = 20;
    private Integer keepAliveTime = 60;

    private ListeningExecutorService executorService;
    private ListeningScheduledExecutorService scheduledExecutorService;

    private static ThreadPoolFactory instance;

    private ThreadPoolFactory() {
        createListeningExecutorService();
        createListeningScheduledExecutorService();
    }

    public static ListeningExecutorService getListeningExecutorService() throws Exception {
        if (instance == null) {
            instance = new ThreadPoolFactory();
        }

        return instance.getExecutorService();
    }

    public static ListeningScheduledExecutorService getListeningScheduledExecutorService() throws Exception {
        if (instance == null) {
            instance = new ThreadPoolFactory();
        }

        return instance.getScheduledExecutorService();
    }

    private void createListeningExecutorService() {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue(queueCapacity, true);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, queue, new ThreadPoolExecutor.CallerRunsPolicy());
        executor.prestartAllCoreThreads();

        executorService = MoreExecutors.listeningDecorator(executor);
    }

    private void createListeningScheduledExecutorService() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(corePoolSize);
        scheduledExecutorService = MoreExecutors.listeningDecorator(executor);
    }

    private ListeningExecutorService getExecutorService() throws Exception {
        return executorService;
    }

    private ListeningScheduledExecutorService getScheduledExecutorService() throws Exception {
        return scheduledExecutorService;
    }
}
