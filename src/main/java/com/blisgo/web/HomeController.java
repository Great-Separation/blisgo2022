package com.blisgo.web;

import com.blisgo.service.HomeService;
import com.blisgo.web.dto.DictionaryDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    // AlgoliaSearch algoliaSearch = new AlgoliaSearch();

    private final HomeService homeService;
    private final ModelAndView mv = new ModelAndView();
    String url;

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
        List<DictionaryDTO> recentProducts = homeService.findRecentDictionaries();
        mv.addObject("recentProducts", recentProducts);
        url = RouteUrlHelper.combine(folder.cmmn, page.index);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 분리배출에 대한 안내가 기재된 페이지
     *
     * @return mv
     */
    @GetMapping("faq")
    public ModelAndView faq(ModelAndView mv) {
        url = RouteUrlHelper.combine(folder.cmmn, page.faq);
        mv.setViewName(url);
        return mv;
    }
}
