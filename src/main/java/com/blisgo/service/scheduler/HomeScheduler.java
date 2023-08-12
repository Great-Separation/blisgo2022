package com.blisgo.service.scheduler;

import com.blisgo.service.HomeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class HomeScheduler {
    private final HomeService homeService;

    public HomeScheduler(HomeService homeService) {
        this.homeService = homeService;
    }

    @Scheduled(zone = "Asia/Seoul", cron = "0 0 0 * * *")
    public void changeIndexWallpaperDaily() {
        homeService.changeIndexWallpaperDaily();
    }
}