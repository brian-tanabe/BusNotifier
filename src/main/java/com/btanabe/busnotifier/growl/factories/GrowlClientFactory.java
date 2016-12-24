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
        log.info(String.format("Creating GntpClient: applicationInfo.name=[%s], applicationInfo.notificationInfos=%s, host=[%s], port=[%s], listener=[%s]",
                applicationInfo.getName(),
                applicationInfo.getNotificationInfos(),
                host,
                port,
                listener.getClass().getName()));
    }

    private void logPostClientConstruction(GntpClient client) {
        log.info(String.format("Client registered successfully=[%s]", client.isRegistered()));
    }
}
