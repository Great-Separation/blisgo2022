package com.blisgo.web;

import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.service.DictionaryService;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DictionaryDTO;
import com.blisgo.web.dto.HashtagDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("dictionary")
public class DictionaryController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DictionaryController.class);
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
    public ModelAndView product(DictionaryDTO dictionaryDTO, HttpServletRequest request) {
        dictionaryService.findDictionary(dictionaryDTO);

        if (!dictionaryService.countDictionaryHit(dictionaryDTO)) {
            log.error("사전 조회수 증가 실패");
        }
        mv.addObject("dictionary", dictionaryDTO);
        List<HashtagDTO> hashtagAndGuide = dictionaryService.findHashtag(dictionaryDTO);
        List<DictionaryDTO> relatedDictionaries = dictionaryService.findRelatedDictionaries(hashtagAndGuide);
        //mv.addObject("requestURI", request.getRequestURI());
        mv.addObject("hashtagAndGuide", hashtagAndGuide);
        mv.addObject("relatedDictionaries", relatedDictionaries);
        url = RouteUrlHelper.combine(folder.dictionary, page.waste);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 북마크 추가 기능
     *
     * @param dictionaryDTO 폐기물
     * @param principal     인증된 사용자
     * @return 북마크 추가
     */
    @PutMapping("dogam/{dicNo}")
    public @ResponseBody boolean addBookmark(DictionaryDTO dictionaryDTO, @AuthenticationPrincipal PrincipalDetails principal) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());
        return dictionaryService.addDogam(dictionaryDTO, accountDTO);
    }

}
