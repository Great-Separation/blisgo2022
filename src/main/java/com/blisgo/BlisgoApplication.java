package com.blisgo;

import com.blisgo.config.AirbrakeAPMConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
public class BlisgoApplication {
    @PostConstruct
    public void started() {
        // 서버 시간 표준으로 고정(UTC+-0)
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        AirbrakeAPMConfig apmMonitor = new AirbrakeAPMConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(BlisgoApplication.class, args);
    }

}
