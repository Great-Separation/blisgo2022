package com.blisgo.service.impl;

import com.blisgo.domain.mapper.DictionaryMapper;
import com.blisgo.domain.repository.DictionaryRepository;
import com.blisgo.service.HomeService;
import com.blisgo.web.dto.DictionaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final DictionaryRepository dictionaryRepository;

    @Override
    public List<DictionaryDTO> findRecentDictionaries() {
        var rs = dictionaryRepository.selectRecentDictionaryList();
        return DictionaryMapper.INSTANCE.toDTOList(rs);
    }

}
