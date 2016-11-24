package com.btanabe.busnotifier.growl.factories;

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpListener;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Brian on 11/20/16.
 */
@Slf4j
@RequiredArgsConstructor
public class GrowlClientFactory {

    @NonNull
    private String host;

    @NonNull
    private Integer port;

    @NonNull
    private GntpListener listener;

    public GntpClient createClient(GntpApplicationInfo applicationInfo) throws Exception {
        logPreClientConstruction(applicationInfo);

        GntpClient client = Gntp.client(applicationInfo).forHost(host).onPort(port).listener(listener).build();
        client.register();

        logPostClientConstruction(client);

        return client;
    }

    private void logPreClientConstruction(GntpApplicationInfo applicationInfo) {
        log.info("About to register GNTP client");
        log.info(String.format("applicationInfo name=[%s]", applicationInfo.getName()));
        log.info(String.format("applicationInfo: notificationInfos=%s", applicationInfo.getNotificationInfos()));
        log.info(String.format("host=[%s]", host));
        log.info(String.format("port=[%s]", port));
        log.info(String.format("listener=[%s]", listener.getClass().getName()));
    }

    private void logPostClientConstruction(GntpClient client) {
        log.info(String.format("Client registered successfully=[%s]", client.isRegistered()));
    }
}
