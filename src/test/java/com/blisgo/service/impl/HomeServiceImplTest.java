package com.blisgo.service.impl;

import com.blisgo.domain.repository.DictionaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {HomeServiceImpl.class})
@ExtendWith(SpringExtension.class)
class HomeServiceImplTest {
    @MockBean
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private HomeServiceImpl homeServiceImpl;

    /**
     * Method under test: {@link HomeServiceImpl#findRecentDictionaries()}
     */
    @Test
    void testFindRecentDictionaries() {
        when(dictionaryRepository.selectRecentDictionaryList()).thenReturn(new ArrayList<>());
        assertTrue(homeServiceImpl.findRecentDictionaries().isEmpty());
        verify(dictionaryRepository).selectRecentDictionaryList();
    }
}

