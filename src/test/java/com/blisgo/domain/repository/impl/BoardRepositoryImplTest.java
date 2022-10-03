package com.blisgo.domain.repository.impl;

import com.blisgo.config.TestQueryDslConfig;
import com.blisgo.domain.entity.Board;
import com.blisgo.domain.repository.BoardRepository;
import com.blisgo.web.dto.BoardDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Slf4j
@DataJpaTest
@Import(TestQueryDslConfig.class)
class BoardRepositoryImplTest extends TestRepositoryTemplate {

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        initData();
        entityAssistant(entityAssistOpt.PERSIST, sampleAccount, sampleBoard);
    }

    @AfterEach
    void tearDown() {
        printJaversMonitorResult();
        entityManager.clear();
        entityAssistant(entityAssistOpt.AUTOINCREMEMT, sampleAccount, sampleBoard);
    }

    @Nested
    @DisplayName("board.html")
    class BoardPage {
        @Test
        @DisplayName("게시글 다건 조회되는가?")
        void testSelectBoardList() {
            List<BoardDTO> result = boardRepository.selectBoardList(0, 24);
            Assertions.assertNotNull(result);
            //diff = javers.compare(sampleBoard, result.get(0));
            //Assertions.assertFalse(diff.hasChanges());
        }
    }

    @Nested
    @DisplayName("content.html")
    class ContentPage {
        @Test
        @DisplayName("게시글이 추가되었는가?")
        void testInsertBoard() {
            boolean result = boardRepository.insertBoard(sampleBoard);
            Assertions.assertTrue(result);
        }

        @Test
        @DisplayName("게시글 단건 조회되는가?")
        void testSelectBoard() {
            Board result = boardRepository.selectBoard(sampleBoard);
            diff = javers.compare(sampleBoard, result);
            Assertions.assertNotNull(result);
            //Assertions.assertFalse(diff.hasChanges());
        }

        @Test
        @DisplayName("게시글이 삭제되었는가?")
        void testDeleteBoard() {
            boolean result = boardRepository.deleteBoard(sampleBoard);
            Assertions.assertTrue(result);
        }

        @Test
        @DisplayName("게시글 조회수가 올랐는가?")
        void testUpdateBoardViews() {
            boolean result = boardRepository.updateBoardViews(sampleBoard);
            Assertions.assertTrue(result);
        }

        @Test
        @DisplayName("게시글이 수정되었는가?")
        void testUpdateBoard() {
            boolean result = boardRepository.updateBoard(sampleBoard);
            Assertions.assertTrue(result);
        }

        @Test
        @DisplayName("게시글 좋아요가 올랐는가?")
        void testUpdateBoardFavorite() {
            boolean result = boardRepository.updateBoardFavorite(sampleBoard);
            Assertions.assertTrue(result);
        }
    }


}