package com.blisgo.web;

import com.blisgo.service.DictionaryService;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DictionaryDTO;
import com.blisgo.web.dto.HashtagDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {DictionaryController.class})
@ExtendWith(SpringExtension.class)
class DictionaryControllerTest {
    @Autowired
    private DictionaryController dictionaryController;

    @MockBean
    private DictionaryService dictionaryService;

    /**
     * Method under test: {@link DictionaryController#addBookmark(HttpSession, DictionaryDTO, AccountDTO)}
     */
    @Test
    void testAddBookmark() throws Exception {
        when(dictionaryService.addDogam((DictionaryDTO) any(), (AccountDTO) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/dictionary/dogam/*");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(dictionaryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link DictionaryController#addBookmark(HttpSession, DictionaryDTO, AccountDTO)}
     */
    @Test
    void testAddBookmark2() throws Exception {
        when(dictionaryService.addDogam((DictionaryDTO) any(), (AccountDTO) any())).thenReturn(true);
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/dictionary/dogam/*");
        putResult.characterEncoding("Encoding");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(dictionaryController)
                .build()
                .perform(putResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link DictionaryController#dictionaries()}
     */
    @Test
    void testDictionaries() throws Exception {
        when(dictionaryService.findDictionaries()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/dictionary");
        MockMvcBuilders.standaloneSetup(dictionaryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dictionaries"))
                .andExpect(MockMvcResultMatchers.view().name("/dictionary/wastes"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/dictionary/wastes"));
    }

    /**
     * Method under test: {@link DictionaryController#dictionaries()}
     */
    @Test
    void testDictionaries2() throws Exception {
        when(dictionaryService.findDictionaries()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/dictionary");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(dictionaryController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dictionaries"))
                .andExpect(MockMvcResultMatchers.view().name("/dictionary/wastes"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/dictionary/wastes"));
    }

    /**
     * Method under test: {@link DictionaryController#dictionaryLoadMore()}
     */
    @Test
    void testDictionaryLoadMore() throws Exception {
        when(dictionaryService.findDictionaryMore()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dictionary/more");
        MockMvcBuilders.standaloneSetup(dictionaryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link DictionaryController#dictionaryLoadMore()}
     */
    @Test
    void testDictionaryLoadMore2() throws Exception {
        when(dictionaryService.findDictionaryMore()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/dictionary/more");
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(dictionaryController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link DictionaryController#product(DictionaryDTO)}
     */
    @Test
    void testProduct() throws Exception {
        when(dictionaryService.countDictionaryHit((DictionaryDTO) any())).thenReturn(true);
        when(dictionaryService.findDictionary((DictionaryDTO) any())).thenReturn(new DictionaryDTO());
        when(dictionaryService.findHashtag((DictionaryDTO) any())).thenReturn(new ArrayList<>());
        when(dictionaryService.findRelatedDictionaries((List<HashtagDTO>) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/dictionary/*");
        MockMvcBuilders.standaloneSetup(dictionaryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("dictionaries", "dictionary", "dictionaryDTO", "hashtagAndGuide", "relatedDictionaries"))
                .andExpect(MockMvcResultMatchers.view().name("/dictionary/waste"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/dictionary/waste"));
    }
}

