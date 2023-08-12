package com.blisgo.web.controller;

import com.blisgo.constant.Folder;
import com.blisgo.constant.Page;
import com.blisgo.service.HomeService;
import com.blisgo.util.RouteUrlHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    // todo 직접 기입했던 algolia 데이터를 database 에서 주기적으로 동기화 되도록 스케줄링
    // AlgoliaSearch algoliaSearch = new AlgoliaSearch();

    private final HomeService homeService;

    public static String wallpaperUrl = null;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    /**
     * 첫 화면
     *
     * @return mv
     */
    @GetMapping
    public ModelAndView index() {
        if (wallpaperUrl.isEmpty()) {
            homeService.changeIndexWallpaperDaily();
        }
        return new ModelAndView(
                RouteUrlHelper.combine(Folder.cmmn, Page.index),
                Map.of(
                        "recentProducts", homeService.findRecentDictionaries(),
                        "wallpaper", wallpaperUrl
                )
        );
    }

    /**
     * 분리배출에 대한 안내가 기재된 페이지
     *
     * @return mv
     */
    @GetMapping("faq")
    public ModelAndView faq() {
        return new ModelAndView(
                RouteUrlHelper.combine(Folder.cmmn, Page.faq)
        );
    }
}