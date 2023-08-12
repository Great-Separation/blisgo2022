package com.blisgo.service;

import com.blisgo.web.dto.DictionaryDTO;
import com.blisgo.web.dto.HashtagDTO;

import java.util.List;
import java.util.Optional;

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
     * @param dicNo 폐기물
     * @return 단건
     */
    Optional<DictionaryDTO> findDictionary(int dicNo);

    /**
     * 분리배출 가이드 출력 메서드
     *
     * @param dicNo 폐기물
     * @return 다건
     */
    List<HashtagDTO> findHashtag(int dicNo);

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
     */
    boolean modifyDictionaryPopularity();

    /**
     * 조회수 추가 메서드
     *
     * @param dicNo 폐기물
     */
    boolean countDictionaryHit(int dicNo);

    /**
     * 북마크 기능 메서드
     *
     * @param dicNo 폐기물
     * @param memNo 사용자
     * @return 북마크 추가
     */
    boolean addDogam(int dicNo, int memNo);
}
