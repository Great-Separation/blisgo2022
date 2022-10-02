package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.entity.Hashtag;
import com.blisgo.domain.entity.cmmn.Wastes;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DictionaryRepository extends Repository<Dictionary, Integer> {
    List<Dictionary> selectRecentDictionaryList();

    List<Dictionary> selectDictionaryList(int index, int limit);

    Dictionary selectDictionary(Dictionary dictionaryEntity);

    List<Dictionary> selectRelatedDictionaryList(Wastes guideCode);

    boolean updateDictionaryPopularity();

    boolean updateDictionaryHit(Dictionary dictionaryEntity);

    List<Hashtag> selectHashtagInnerJoinGuide(Dictionary dictionaryEntity);

    boolean insertDogam(Dogam dogamEntity);
}
