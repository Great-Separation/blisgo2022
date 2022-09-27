package com.blisgo.service;

import com.blisgo.web.dto.DictionaryDTO;
import com.blisgo.web.dto.HashtagDTO;
import com.blisgo.web.dto.UserDTO;

import java.util.List;

public interface DictionaryService {

    /**
     * 사전 조회 메서드
     *
     * @return 다건
     */
    List<DictionaryDTO> findDictionaries();

    /**
     * 물품 상세 내용 보는 메서드
     *
     * @param dictionaryDTO 폐기물
     * @return 단건
     */
    DictionaryDTO findDictionary(DictionaryDTO dictionaryDTO);

    /**
     * 분리배출 가이드 출력 메서드
     *
     * @param dictionaryDTO 폐기물
     * @return 다건
     */
    List<HashtagDTO> findHashtag(DictionaryDTO dictionaryDTO);

    /**
     * 연관 물품 나열 메서드
     *
     * @param hashtagDTO 해시태그
     * @return 다건
     */
    List<DictionaryDTO> findRelatedDictionaries(List<HashtagDTO> hashtagDTO);

    /**
     * 물품 더보기 메서드
     *
     * @return 폐기물 다건
     */
    List<DictionaryDTO> findDictionaryMore();


    /**
     * 별점 매기는 메서드
     * TODO [modifyDictionaryPopularity] 실행 결과 반드시 반환
     */
    void modifyDictionaryPopularity();

    /**
     * 조회수 추가 메서드
     *
     * @param dictionary 폐기물 TODO [countDictionaryHit] 실행 결과 반드시 반환
     */
    void countDictionaryHit(DictionaryDTO dictionary);

    /**
     * 북마크 기능 메서드
     *
     * @param dictionaryDTO 폐기물
     * @param userDTO       사용자
     * @return 북마크 추가
     */
    boolean addDogam(DictionaryDTO dictionaryDTO, UserDTO userDTO);
}
