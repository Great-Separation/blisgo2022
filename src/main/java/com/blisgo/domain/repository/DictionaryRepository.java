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

    Dictionary selectDictionary(int dicNo);

    List<Dictionary> selectRelatedDictionaryList(List<Wastes> tags);

    boolean updateDictionaryPopularity();

    boolean updateDictionaryHit(int dicNo);

    List<Hashtag> selectHashtagInnerJoinGuide(int dicNo);

    boolean insertDogam(Dogam dogamEntity);
}
