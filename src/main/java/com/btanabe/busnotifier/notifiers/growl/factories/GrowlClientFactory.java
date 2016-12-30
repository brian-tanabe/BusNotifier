package com.btanabe.busnotifier.notifiers.growl.factories;

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpListener;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Brian on 11/20/16.
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class GrowlClientFactory {

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private String host;

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
    private Integer port;

    @NonNull
    @Setter(onMethod = @__({@Autowired}))
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
