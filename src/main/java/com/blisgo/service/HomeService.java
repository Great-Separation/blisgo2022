package com.blisgo.service;

import com.blisgo.web.dto.DictionaryDTO;

import java.util.List;

public interface HomeService {

    /**
     * 최근 등록된 물품 출력 메서드
     *
     * @return 다건
     */
    List<DictionaryDTO> findRecentDictionaries();
}
