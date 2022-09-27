package com.blisgo.web;

import com.blisgo.service.DictionaryService;
import com.blisgo.web.dto.DictionaryDTO;
import com.blisgo.web.dto.HashtagDTO;
import com.blisgo.web.dto.UserDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;
    private final ModelAndView mv = new ModelAndView();
    String url;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    /**
     * 분리배출 사전
     *
     * @return mv
     */
    @GetMapping
    public ModelAndView dictionaries() {
        List<DictionaryDTO> dictionaries = dictionaryService.findDictionaries();
        mv.addObject("dictionaries", dictionaries);
        url = RouteUrlHelper.combine(folder.dictionary, page.wastes);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 사전 더보기
     *
     * @return 사전 목록
     */
    @PostMapping("more")
    public @ResponseBody List<DictionaryDTO> dictionaryLoadMore() {

        return dictionaryService.findDictionaryMore();
    }

    /**
     * 분리배출 물품 안내
     *
     * @param dictionaryDTO 폐기물
     * @return mv
     */
    @GetMapping("{dicNo}")
    public ModelAndView product(DictionaryDTO dictionaryDTO) {

        dictionaryDTO = dictionaryService.findDictionary(dictionaryDTO);

        dictionaryService.countDictionaryHit(dictionaryDTO);

        mv.addObject("dictionary", dictionaryDTO);

        List<HashtagDTO> hashtagAndGuide = dictionaryService.findHashtag(dictionaryDTO);


        List<DictionaryDTO> relatedDictionaries = dictionaryService.findRelatedDictionaries(hashtagAndGuide);

        mv.addObject("hashtagAndGuide", hashtagAndGuide);
        mv.addObject("relatedDictionaries", relatedDictionaries);

        url = RouteUrlHelper.combine(folder.dictionary, page.waste);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 북마크 추가 기능
     *
     * @param session       세션
     * @param dictionaryDTO 폐기물
     * @param userDTO       사용자
     * @return 북마크 추가
     */
    @PutMapping("dogam/{dicNo}")
    public @ResponseBody boolean addBookmark(HttpSession session, DictionaryDTO dictionaryDTO, UserDTO userDTO) {
        userDTO = (UserDTO) session.getAttribute("mem");

        return dictionaryService.addDogam(dictionaryDTO, userDTO);
    }

}
