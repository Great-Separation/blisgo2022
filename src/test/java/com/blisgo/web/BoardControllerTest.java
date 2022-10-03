package com.blisgo.web;

import com.blisgo.domain.repository.BoardRepository;
import com.blisgo.domain.repository.ReplyRepository;
import com.blisgo.service.BoardService;
import com.blisgo.service.ReplyService;
import com.blisgo.service.impl.BoardServiceImpl;
import com.blisgo.service.impl.ReplyServiceImpl;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BoardController.class})
@ExtendWith(SpringExtension.class)
class BoardControllerTest {
    @Autowired
    private BoardController boardController;

    @MockBean
    private BoardService boardService;

    @MockBean
    private ReplyService replyService;

    /**
     * Method under test: {@link BoardController#boardLoadMore()}
     */
    @Test
    void testBoardLoadMore() throws Exception {
        when(boardService.findBoardMore()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/board/more");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link BoardController#uploadToCloudinary(MultipartFile)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUploadToCloudinary() throws IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.io.File.exists()" because "uuidFile" is null
        //       at com.blisgo.util.CloudinaryUtil.uploadFile(CloudinaryUtil.java:73)
        //       at com.blisgo.web.BoardController.uploadToCloudinary(BoardController.java:163)
        //   In order to prevent uploadToCloudinary(MultipartFile)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   uploadToCloudinary(MultipartFile).
        //   See https://diff.blue/R013 to resolve this issue.

        BoardServiceImpl boardService = new BoardServiceImpl(mock(BoardRepository.class));
        BoardController boardController = new BoardController(boardService,
                new ReplyServiceImpl(mock(ReplyRepository.class)));
        boardController
                .uploadToCloudinary(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
    }

    /**
     * Method under test: {@link BoardController#boardLoadMore()}
     */
    @Test
    void testBoardLoadMore2() throws Exception {
        when(boardService.findBoardMore()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/board/more");
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link BoardController#community()}
     */
    @Test
    void testCommunity() throws Exception {
        when(boardService.findBoards()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/board");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("boards"))
                .andExpect(MockMvcResultMatchers.view().name("/community/board"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/community/board"));
    }

    /**
     * Method under test: {@link BoardController#community()}
     */
    @Test
    void testCommunity2() throws Exception {
        when(boardService.findBoards()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/board");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("boards"))
                .andExpect(MockMvcResultMatchers.view().name("/community/board"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/community/board"));
    }

    /**
     * Method under test: {@link BoardController#content(BoardDTO)}
     */
    @Test
    void testContent() throws Exception {
        when(boardService.countBoardViews((BoardDTO) any())).thenReturn(true);
        when(boardService.findBoard((BoardDTO) any())).thenReturn(new BoardDTO());
        when(replyService.findReply((BoardDTO) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/board/*");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board", "boardDTO", "boards", "replys"))
                .andExpect(MockMvcResultMatchers.view().name("/community/content"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/community/content"));
    }

    /**
     * Method under test: {@link BoardController#contentDelete(BoardDTO)}
     */
    @Test
    void testContentDelete() throws Exception {
        when(boardService.removeBoard((BoardDTO) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/board/*");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board", "boardDTO", "boards", "replys"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board"));
    }

    /**
     * Method under test: {@link BoardController#contentDelete(BoardDTO)}
     */
    @Test
    void testContentDelete2() throws Exception {
        when(boardService.removeBoard((BoardDTO) any())).thenReturn(true);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/board/*");
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board", "boardDTO", "boards", "replys"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board"));
    }

    /**
     * Method under test: {@link BoardController#contentEdit(BoardDTO)}
     */
    @Test
    void testContentEdit() throws Exception {
        when(boardService.findBoard((BoardDTO) any())).thenReturn(new BoardDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/board/edit/*");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board", "boardDTO", "boards", "replys"))
                .andExpect(MockMvcResultMatchers.view().name("/community/edit"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/community/edit"));
    }

    /**
     * Method under test: {@link BoardController#contentEditPut(BoardDTO)}
     */
    @Test
    void testContentEditPut() throws Exception {
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/board/edit/*");
        MockHttpServletRequestBuilder requestBuilder = putResult.param("boardDTO", String.valueOf(new BoardDTO()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link BoardController#likeBoard(BoardDTO)}
     */
    @Test
    void testLikeBoard() throws Exception {
        when(boardService.countBoardFavorite((BoardDTO) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/board/*/like");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board", "boardDTO", "boards", "replys"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board/null"));
    }

    /**
     * Method under test: {@link BoardController#likeBoard(BoardDTO)}
     */
    @Test
    void testLikeBoard2() throws Exception {
        when(boardService.countBoardFavorite((BoardDTO) any())).thenReturn(true);
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/board/*/like");
        putResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(putResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board", "boardDTO", "boards", "replys"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board/null"));
    }

    /**
     * Method under test: {@link BoardController#write()}
     */
    @Test
    void testWrite() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/board/write");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board", "boards", "replys"))
                .andExpect(MockMvcResultMatchers.view().name("/community/write"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/community/write"));
    }

    /**
     * Method under test: {@link BoardController#write()}
     */
    @Test
    void testWrite2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/board/write");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board", "boards", "replys"))
                .andExpect(MockMvcResultMatchers.view().name("/community/write"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/community/write"));
    }

    /**
     * Method under test: {@link BoardController#writePOST(HttpSession, BoardDTO, AccountDTO)}
     */
    @Test
    void testWritePOST() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/board/write");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("boardDTO", String.valueOf(new BoardDTO()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(boardController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

