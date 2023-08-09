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
import com.blisgo.web.dto.GuideDTO;
import com.blisgo.web.dto.HashtagDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepository dictionaryRepository;

    private static int index = 0;
    private static final int limit = 24;

    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public List<DictionaryDTO> findDictionaries() {
        index = 0;
        var rs = dictionaryRepository.selectDictionaryList(index, limit);
        return DictionaryMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public Optional<DictionaryDTO> findDictionary(DictionaryDTO dictionaryDTO) {
        var dictionary = DictionaryMapper.INSTANCE.toEntity(dictionaryDTO);
        var rs = dictionaryRepository.selectDictionary(dictionary);
        return Optional.ofNullable(DictionaryMapper.INSTANCE.toDTO(rs));
    }

    @Override
    public List<HashtagDTO> findHashtag(DictionaryDTO dictionaryDTO) {
        var dictionary = DictionaryMapper.INSTANCE.toEntity(dictionaryDTO);
        var rs = dictionaryRepository.selectHashtagInnerJoinGuide(dictionary);
        return HashtagMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public List<DictionaryDTO> findRelatedDictionaries(List<HashtagDTO> hashtagDTO) {
        List<Wastes> tags = hashtagDTO.stream().map(HashtagDTO::guide).map(GuideDTO::guideCode).collect(Collectors.toList());
        var rs = dictionaryRepository.selectRelatedDictionaryList(tags);
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
