package com.blisgo.web.controller;

import com.blisgo.constant.Folder;
import com.blisgo.constant.Page;
import com.blisgo.exception.GeneralException;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.service.DictionaryService;
import com.blisgo.util.RouteUrlHelper;
import com.blisgo.web.dto.DictionaryDTO;
import com.blisgo.web.dto.HashtagDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;

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
        return new ModelAndView(
                RouteUrlHelper.combine(Folder.dictionary, Page.wastes),
                Map.of("dictionaries", dictionaryService.findDictionaries())
        );
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
     * @param dicNo   폐기물 번호
     * @param request 요청
     * @return mv
     */
    @GetMapping("{dicNo}")
    public ModelAndView product(
            @PathVariable int dicNo,
            HttpServletRequest request
    ) {
        DictionaryDTO dictionaryDTO = dictionaryService.findDictionary(dicNo)
                .orElseThrow(() -> new GeneralException("존재하지 않는 폐기물 식별번호입니다"));

        dictionaryService.countDictionaryHit(dicNo);
        List<HashtagDTO> hashtagAndGuide = dictionaryService.findHashtag(dicNo);

        return new ModelAndView(
                RouteUrlHelper.combine(Folder.dictionary, Page.waste),
                Map.of(
                        "requestURI", request.getRequestURI(),
                        "dictionary", dictionaryDTO,
                        "hashtagAndGuide", hashtagAndGuide,
                        "relatedDictionaries", dictionaryService.findRelatedDictionaries(hashtagAndGuide)
                )
        );
    }


    /**
     * 북마크 추가 기능
     *
     * @param dicNo     폐기물 번호
     * @param principal 인증된 사용자
     * @return 북마크 추가
     */
    @PutMapping("dogam/{dicNo}")
    public @ResponseBody boolean addBookmark(
            @PathVariable int dicNo,
            @AuthenticationPrincipal PrincipalDetails principal
    ) {
        return dictionaryService.addDogam(dicNo, principal.getAccount().getMemNo());
    }
}