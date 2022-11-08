package com.blisgo.service.scheduler;

import com.blisgo.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class HomeScheduler {
    private final HomeService homeService;

    @SuppressWarnings("unused")
    public void changeIndexWallpaperDaily() {
        homeService.changeIndexWallpaperDaily();
    }
}
