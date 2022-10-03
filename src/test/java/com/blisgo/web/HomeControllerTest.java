package com.blisgo.web;

import static org.mockito.Mockito.when;

import com.blisgo.service.HomeService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

@ContextConfiguration(classes = {HomeController.class})
@ExtendWith(SpringExtension.class)
class HomeControllerTest {
    @Autowired
    private HomeController homeController;

    @MockBean
    private HomeService homeService;

    /**
     * Method under test: {@link HomeController#faq(ModelAndView)}
     */
    @Test
    void testFaq() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/faq");
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("modelAndView"))
                .andExpect(MockMvcResultMatchers.view().name("/cmmn/faq"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/cmmn/faq"));
    }

    /**
     * Method under test: {@link HomeController#faq(ModelAndView)}
     */
    @Test
    void testFaq2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/faq", "Uri Variables");
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("modelAndView"))
                .andExpect(MockMvcResultMatchers.view().name("/cmmn/faq"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/cmmn/faq"));
    }

    /**
     * Method under test: {@link HomeController#index()}
     */
    @Test
    void testIndex() throws Exception {
        when(homeService.findRecentDictionaries()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recentProducts"))
                .andExpect(MockMvcResultMatchers.view().name("/cmmn/index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/cmmn/index"));
    }

    /**
     * Method under test: {@link HomeController#index()}
     */
    @Test
    void testIndex2() throws Exception {
        when(homeService.findRecentDictionaries()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recentProducts"))
                .andExpect(MockMvcResultMatchers.view().name("/cmmn/index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/cmmn/index"));
    }
}

