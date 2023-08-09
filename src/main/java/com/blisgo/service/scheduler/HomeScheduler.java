package com.blisgo.service.scheduler;

import com.blisgo.service.HomeService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class HomeScheduler {
    private final HomeService homeService;

    public HomeScheduler(HomeService homeService) {
        this.homeService = homeService;
    }

    @SuppressWarnings("unused")
    public void changeIndexWallpaperDaily() throws JSONException, IOException, InterruptedException {
        homeService.changeIndexWallpaperDaily();
    }
}
