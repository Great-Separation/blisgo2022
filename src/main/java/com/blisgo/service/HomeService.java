package com.blisgo.service;

import com.blisgo.web.dto.DictionaryDTO;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface HomeService {

    /**
     * 최근 등록된 물품 출력 메서드
     *
     * @return 다건
     */
    List<DictionaryDTO> findRecentDictionaries();

    /**
     * index 페이지 배경 화면을 매일 변경하는 베서드
     */
    void changeIndexWallpaperDaily() throws JSONException, IOException, InterruptedException;
}
