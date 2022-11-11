package com.blisgo.web;

import com.blisgo.service.HomeService;
import com.blisgo.util.Unsplash;
import com.blisgo.web.dto.DictionaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    // AlgoliaSearch algoliaSearch = new AlgoliaSearch();

    private final HomeService homeService;
    private final ModelAndView mv = new ModelAndView();
    String url;
    // 서버 실행 시 index 배경 화면 변경. 1 client -> 1 서버 스레드 실행. 시간 당 50최대
    public static String wallpaperUrl = null;

    /**
     * 첫 화면
     *
     * @return mv
     */
    @GetMapping
    public ModelAndView index() {
        List<DictionaryDTO> recentProducts = homeService.findRecentDictionaries();
        mv.addObject("recentProducts", recentProducts);
        url = RouteUrlHelper.combine(folder.cmmn, page.index);
        mv.setViewName(url);
        if (wallpaperUrl == null) {
            wallpaperUrl = Unsplash.getImageUrl();
        }
        mv.addObject("wallpaper", wallpaperUrl);
        return mv;
    }

    /**
     * 분리배출에 대한 안내가 기재된 페이지
     *
     * @return mv
     */
    @GetMapping("faq")
    public ModelAndView faq() {
        url = RouteUrlHelper.combine(folder.cmmn, page.faq);
        mv.setViewName(url);
        return mv;
    }
}
