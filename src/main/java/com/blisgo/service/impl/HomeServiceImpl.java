package com.blisgo.service.impl;

import com.blisgo.domain.mapper.DictionaryMapper;
import com.blisgo.domain.repository.DictionaryRepository;
import com.blisgo.service.HomeService;
import com.blisgo.util.Unsplash;
import com.blisgo.web.controller.HomeController;
import com.blisgo.web.dto.DictionaryDTO;
import org.slf4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(HomeServiceImpl.class);
    private final DictionaryRepository dictionaryRepository;

    public HomeServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public List<DictionaryDTO> findRecentDictionaries() {
        return DictionaryMapper.INSTANCE.toDTOList(
                dictionaryRepository.selectRecentDictionaryList()
        );
    }

    @Override
    public void changeIndexWallpaperDaily() {
        try {
            HomeController.wallpaperUrl = Unsplash.getImageUrl();
        } catch (JSONException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
