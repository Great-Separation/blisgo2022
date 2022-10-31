package com.blisgo.service.impl;

import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.entity.cmmn.Wastes;
import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.domain.mapper.DictionaryMapper;
import com.blisgo.domain.mapper.HashtagMapper;
import com.blisgo.domain.repository.DictionaryRepository;
import com.blisgo.service.DictionaryService;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DictionaryDTO;
import com.blisgo.web.dto.HashtagDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    private static int index = 0;
    private static final int limit = 24;

    @Override
    public List<DictionaryDTO> findDictionaries() {
        index = 0;
        var rs = dictionaryRepository.selectDictionaryList(index, limit);
        return DictionaryMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public DictionaryDTO findDictionary(DictionaryDTO dictionaryDTO) {
        var dictionary = DictionaryMapper.INSTANCE.toEntity(dictionaryDTO);
        var rs = dictionaryRepository.selectDictionary(dictionary);
        return DictionaryMapper.INSTANCE.toDTO(rs);
    }

    @Override
    public List<HashtagDTO> findHashtag(DictionaryDTO dictionaryDTO) {
        var dictionary = DictionaryMapper.INSTANCE.toEntity(dictionaryDTO);
        var rs = dictionaryRepository.selectHashtagInnerJoinGuide(dictionary);
        return HashtagMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public List<DictionaryDTO> findRelatedDictionaries(List<HashtagDTO> hashtagDTO) {
        // 선택한 product의 중분류를 토큰으로 나누어 출력해야할 내용을 저장하여 tabs 출력
        // 선택된 태그중 임의 태그 하나와 관련된 물품들 무작위 4개 조회
        List<Wastes> tags = new ArrayList<>();

        for (HashtagDTO tag : hashtagDTO) {
            tags.add(tag.getGuide().getGuideCode());
        }

        int size = tags.size();
        Random rd = new Random();
        var rs = dictionaryRepository.selectRelatedDictionaryList(tags.get(rd.nextInt(size)));
        return DictionaryMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public List<DictionaryDTO> findDictionaryMore() {
        // 더보기
        index += limit;

        var rs = dictionaryRepository.selectDictionaryList(index, limit);
        return DictionaryMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public boolean modifyDictionaryPopularity() {
        return dictionaryRepository.updateDictionaryPopularity();
    }

    @Override
    public boolean countDictionaryHit(DictionaryDTO dictionaryDTO) {
        var dictionary = DictionaryMapper.INSTANCE.toEntity(dictionaryDTO);
        return dictionaryRepository.updateDictionaryHit(dictionary);
    }

    @Override
    public boolean addDogam(DictionaryDTO dictionaryDTO, AccountDTO accountDTO) {
        var dictionary = DictionaryMapper.INSTANCE.toEntity(dictionaryDTO);
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        var dogam = Dogam.builder().dictionary(dictionary).account(account).build();

        return dictionaryRepository.insertDogam(dogam);
    }

}
