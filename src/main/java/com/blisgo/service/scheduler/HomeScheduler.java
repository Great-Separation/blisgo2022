package com.blisgo.service.scheduler;

import com.blisgo.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeScheduler {
    private final HomeService homeService;

    @Scheduled(zone = "Asia/Seoul", cron = "0 */30 * ? * *")// 매 30분 마다 수행
    public void updateDictionaryPopularity() {
        homeService.changeIndexWallpaperDaily();
    }
}
