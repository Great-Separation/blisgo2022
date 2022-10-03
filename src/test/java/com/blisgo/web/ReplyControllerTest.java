package com.blisgo.web;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.blisgo.service.ReplyService;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ReplyController.class})
@ExtendWith(SpringExtension.class)
class ReplyControllerTest {
    @Autowired
    private ReplyController replyController;

    @MockBean
    private ReplyService replyService;

    /**
     * Method under test: {@link ReplyController#replyPOST(AccountDTO, BoardDTO, ReplyDTO)}
     */
    @Test
    void testReplyPOST() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/reply/*");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("replyDTO", String.valueOf(new ReplyDTO()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(replyController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link ReplyController#replyRemove(BoardDTO, ReplyDTO)}
     */
    @Test
    void testReplyRemove() throws Exception {
        when(replyService.removeReply((ReplyDTO) any(), (BoardDTO) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/reply/*/*");
        MockMvcBuilders.standaloneSetup(replyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("boardDTO", "replyDTO"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board/null"));
    }

    /**
     * Method under test: {@link ReplyController#replyRemove(BoardDTO, ReplyDTO)}
     */
    @Test
    void testReplyRemove2() throws Exception {
        when(replyService.removeReply((ReplyDTO) any(), (BoardDTO) any())).thenReturn(true);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/reply/*/*");
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(replyController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("boardDTO", "replyDTO"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board/null"));
    }
}

