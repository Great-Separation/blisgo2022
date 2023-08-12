package com.blisgo.service.impl;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.entity.cmmn.Wastes;
import com.blisgo.domain.mapper.DictionaryMapper;
import com.blisgo.domain.mapper.HashtagMapper;
import com.blisgo.domain.repository.DictionaryRepository;
import com.blisgo.service.DictionaryService;
import com.blisgo.web.dto.DictionaryDTO;
import com.blisgo.web.dto.GuideDTO;
import com.blisgo.web.dto.HashtagDTO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DictionaryServiceImpl.class);
    private final DictionaryRepository dictionaryRepository;

    private static int index = 0;
    private static final int limit = 48;

    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public List<DictionaryDTO> findDictionaries() {
        index = 0;
        return DictionaryMapper.INSTANCE.toDTOList(
                dictionaryRepository.selectDictionaryList(index, limit)
        );
    }

    @Override
    public Optional<DictionaryDTO> findDictionary(int dicNo) {
        return Optional.ofNullable(
                DictionaryMapper.INSTANCE.toDTO(
                        dictionaryRepository.selectDictionary(dicNo)
                )
        );
    }

    @Override
    public List<HashtagDTO> findHashtag(int dicNo) {
        return HashtagMapper.INSTANCE.toDTOList(
                dictionaryRepository.selectHashtagInnerJoinGuide(dicNo)
        );
    }

    @Override
    public List<DictionaryDTO> findRelatedDictionaries(List<HashtagDTO> hashtagDTO) {
        List<Wastes> tags = hashtagDTO.stream()
                .map(HashtagDTO::guide)
                .map(GuideDTO::guideCode).toList();

        return DictionaryMapper.INSTANCE.toDTOList(
                dictionaryRepository.selectRelatedDictionaryList(tags)
        );
    }

    @Override
    public List<DictionaryDTO> findDictionaryMore() {
        index += limit;
        return DictionaryMapper.INSTANCE.toDTOList(
                dictionaryRepository.selectDictionaryList(index, limit)
        );
    }

    @Override
    public boolean modifyDictionaryPopularity() {
        return dictionaryRepository.updateDictionaryPopularity();
    }

    @Override
    public boolean countDictionaryHit(int dicNo) {
        return dictionaryRepository.updateDictionaryHit(dicNo);
    }

    @Override
    public boolean addDogam(int dicNo, int memNo) {
        return dictionaryRepository.insertDogam(
                Dogam.builder()
                        .dictionary(Dictionary.builder().dicNo(dicNo).build())
                        .account(Account.builder().memNo(memNo).build())
                        .build()
        );
    }

}