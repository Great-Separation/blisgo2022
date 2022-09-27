package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.entity.Hashtag;
import com.blisgo.domain.entity.cmmn.Wastes;

import java.util.List;

public interface DictionaryRepository {
    List<Dictionary> selectRecentDictionaryList();

    List<Dictionary> selectDictionaryList(int index, int limit);

    Dictionary productInfo(Dictionary dictionaryEntity);

    List<Dictionary> selectRelatedDictionaryList(Wastes guideCode);

    void updateDictionaryPopularity();

    void updateDictionaryHit(Dictionary dictionaryEntity);

    List<Hashtag> selectHashtagInnerJoinGuide(Dictionary dictionaryEntity);

    boolean insertDogam(Dogam dogamEntity);
}
