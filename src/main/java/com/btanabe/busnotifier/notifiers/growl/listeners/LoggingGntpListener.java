package com.btanabe.busnotifier.notifiers.growl.listeners;

import com.google.code.jgntp.GntpErrorStatus;
import com.google.code.jgntp.GntpListener;
import com.google.code.jgntp.GntpNotification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Created by Brian on 11/20/16.
 */
@Slf4j
public class LoggingGntpListener implements GntpListener {

    @Override
    public void onRegistrationSuccess() {
        log.info("Registration success");
    }

    @Override
    public void onNotificationSuccess(GntpNotification notification) {
        log.info(String.format("Notification success=[%s]", notification));
    }

    @Override
    public void onClickCallback(GntpNotification notification) {
        log.info(String.format("Click callback=[%s]", notification));
    }

    @Override
    public void onCloseCallback(GntpNotification notification) {
        log.info(String.format("Close callback=[%s]", notification));
    }

    @Override
    public void onTimeoutCallback(GntpNotification notification) {
        log.info(String.format("Timeout callback=[%s]", notification));
    }

    @Override
    public void onRegistrationError(GntpErrorStatus status, String description) {
        log.info(String.format("Registration callback error: status=[%s], description=[%s]", status, description));
    }

    @Override
    public void onNotificationError(GntpNotification notification, GntpErrorStatus status, String description) {
        log.info(String.format("Notification error: notification=[%s], status=[%s], description=[%s]", notification, status, description));
    }

    @Override
    public void onCommunicationError(Throwable t) {
        log.info(String.format("Communication error: exception=[%s], stacktrace: %s", ExceptionUtils.getRootCause(t), ExceptionUtils.getStackTrace(t)));
    }
}
