package com.blisgo;

import com.blisgo.util.Unsplash;
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
        // 서버 실행 시 index 배경 화면 변경. 1 client -> 1 서버 스레드 실행. 시간 당 50최대
        Unsplash.changeWallpaper();
    }

    public static void main(String[] args) {
        SpringApplication.run(BlisgoApplication.class, args);
    }
}
