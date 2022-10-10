package com.blisgo.config;

import io.airbrake.javabrake.Config;
import io.airbrake.javabrake.Notice;
import io.airbrake.javabrake.Notifier;
import io.airbrake.javabrake.OkSender;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public class AirbrakeAPMConfig {

    public AirbrakeAPMConfig() {
        Config config = new Config();
        config.projectId = 456794;
        config.projectKey = "ed3a496107f0c379abd1a01d5ed608c4";
        Notifier notifier = new Notifier(config);

        notifier.addFilter(
                (Notice notice) -> {
                    notice.setContext("environment", "production");
                    return notice;
                });
        String a=null;
        try {
            a.charAt(2);
        } catch (Exception e) {
            notifier.report(e);
        }


    }


}
