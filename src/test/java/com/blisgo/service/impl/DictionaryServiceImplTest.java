package com.blisgo.service.impl;

import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Guide;
import com.blisgo.domain.repository.DictionaryRepository;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DictionaryDTO;
import com.blisgo.web.dto.HashtagDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DictionaryServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DictionaryServiceImplTest {
    @MockBean
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private DictionaryServiceImpl dictionaryServiceImpl;

    /**
     * Method under test: {@link DictionaryServiceImpl#findDictionaries()}
     */
    @Test
    void testFindDictionaries() {
        when(dictionaryRepository.selectDictionaryList(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        assertTrue(dictionaryServiceImpl.findDictionaries().isEmpty());
        verify(dictionaryRepository).selectDictionaryList(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#findDictionary(DictionaryDTO)}
     */
    @Test
    void testFindDictionary() {
        when(dictionaryRepository.selectDictionary(any())).thenReturn(new Dictionary());
        DictionaryDTO actualFindDictionaryResult = dictionaryServiceImpl.findDictionary(new DictionaryDTO());
        assertNull(actualFindDictionaryResult.getCategory());
        assertNull(actualFindDictionaryResult.getTreatment());
        assertNull(actualFindDictionaryResult.getThumbnail());
        assertNull(actualFindDictionaryResult.getPopularity());
        assertNull(actualFindDictionaryResult.getName());
        assertNull(actualFindDictionaryResult.getHit());
        assertNull(actualFindDictionaryResult.getEngName());
        assertNull(actualFindDictionaryResult.getDicNo());
        verify(dictionaryRepository).selectDictionary(any());
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#findHashtag(DictionaryDTO)}
     */
    @Test
    void testFindHashtag() {
        when(dictionaryRepository.selectHashtagInnerJoinGuide(any())).thenReturn(new ArrayList<>());
        assertTrue(dictionaryServiceImpl.findHashtag(new DictionaryDTO()).isEmpty());
        verify(dictionaryRepository).selectHashtagInnerJoinGuide(any());
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#findRelatedDictionaries(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindRelatedDictionaries() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: bound must be positive
        //       at java.util.Random.nextInt(Random.java:322)
        //       at com.blisgo.service.impl.DictionaryServiceImpl.findRelatedDictionaries(DictionaryServiceImpl.java:65)
        //   In order to prevent findRelatedDictionaries(List)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findRelatedDictionaries(List).
        //   See https://diff.blue/R013 to resolve this issue.

        dictionaryServiceImpl.findRelatedDictionaries(new ArrayList<>());
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#findRelatedDictionaries(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindRelatedDictionaries2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.domain.entity.Guide.getGuideCode()" because the return value of "com.blisgo.web.dto.HashtagDTO.getGuide()" is null
        //       at com.blisgo.service.impl.DictionaryServiceImpl.findRelatedDictionaries(DictionaryServiceImpl.java:60)
        //   In order to prevent findRelatedDictionaries(List)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findRelatedDictionaries(List).
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<HashtagDTO> hashtagDTOList = new ArrayList<>();
        hashtagDTOList.add(new HashtagDTO());
        dictionaryServiceImpl.findRelatedDictionaries(hashtagDTOList);
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#findRelatedDictionaries(List)}
     */
    @Test
    void testFindRelatedDictionaries3() {
        when(dictionaryRepository.selectRelatedDictionaryList(Collections.singletonList(any()))).thenReturn(new ArrayList<>());

        ArrayList<HashtagDTO> hashtagDTOList = new ArrayList<>();
        Dictionary dictionary = new Dictionary();
        hashtagDTOList.add(new HashtagDTO(dictionary, new Guide()));
        assertTrue(dictionaryServiceImpl.findRelatedDictionaries(hashtagDTOList).isEmpty());
        verify(dictionaryRepository).selectRelatedDictionaryList(Collections.singletonList(any()));
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#findRelatedDictionaries(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindRelatedDictionaries4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.web.dto.HashtagDTO.getGuide()" because "tag" is null
        //       at com.blisgo.service.impl.DictionaryServiceImpl.findRelatedDictionaries(DictionaryServiceImpl.java:60)
        //   In order to prevent findRelatedDictionaries(List)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   findRelatedDictionaries(List).
        //   See https://diff.blue/R013 to resolve this issue.

        when(dictionaryRepository.selectRelatedDictionaryList(Collections.singletonList(any()))).thenReturn(new ArrayList<>());

        ArrayList<HashtagDTO> hashtagDTOList = new ArrayList<>();
        hashtagDTOList.add(null);
        dictionaryServiceImpl.findRelatedDictionaries(hashtagDTOList);
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#findDictionaryMore()}
     */
    @Test
    void testFindDictionaryMore() {
        when(dictionaryRepository.selectDictionaryList(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        assertTrue(dictionaryServiceImpl.findDictionaryMore().isEmpty());
        verify(dictionaryRepository).selectDictionaryList(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#modifyDictionaryPopularity()}
     */
    @Test
    void testModifyDictionaryPopularity() {
        when(dictionaryRepository.updateDictionaryPopularity()).thenReturn(true);
        assertTrue(dictionaryServiceImpl.modifyDictionaryPopularity());
        verify(dictionaryRepository).updateDictionaryPopularity();
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#modifyDictionaryPopularity()}
     */
    @Test
    void testModifyDictionaryPopularity2() {
        when(dictionaryRepository.updateDictionaryPopularity()).thenReturn(false);
        assertFalse(dictionaryServiceImpl.modifyDictionaryPopularity());
        verify(dictionaryRepository).updateDictionaryPopularity();
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#countDictionaryHit(DictionaryDTO)}
     */
    @Test
    void testCountDictionaryHit() {
        when(dictionaryRepository.updateDictionaryHit(any())).thenReturn(true);
        assertTrue(dictionaryServiceImpl.countDictionaryHit(new DictionaryDTO()));
        verify(dictionaryRepository).updateDictionaryHit(any());
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#countDictionaryHit(DictionaryDTO)}
     */
    @Test
    void testCountDictionaryHit2() {
        when(dictionaryRepository.updateDictionaryHit(any())).thenReturn(false);
        assertFalse(dictionaryServiceImpl.countDictionaryHit(new DictionaryDTO()));
        verify(dictionaryRepository).updateDictionaryHit(any());
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#addDogam(DictionaryDTO, AccountDTO)}
     */
    @Test
    void testAddDogam() {
        when(dictionaryRepository.insertDogam(any())).thenReturn(true);
        DictionaryDTO dictionaryDTO = new DictionaryDTO();
        assertTrue(dictionaryServiceImpl.addDogam(dictionaryDTO, new AccountDTO()));
        verify(dictionaryRepository).insertDogam(any());
    }

    /**
     * Method under test: {@link DictionaryServiceImpl#addDogam(DictionaryDTO, AccountDTO)}
     */
    @Test
    void testAddDogam2() {
        when(dictionaryRepository.insertDogam(any())).thenReturn(false);
        DictionaryDTO dictionaryDTO = new DictionaryDTO();
        assertFalse(dictionaryServiceImpl.addDogam(dictionaryDTO, new AccountDTO()));
        verify(dictionaryRepository).insertDogam(any());
    }
}

