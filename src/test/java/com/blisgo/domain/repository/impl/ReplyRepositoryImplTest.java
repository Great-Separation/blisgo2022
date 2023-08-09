package com.blisgo.domain.repository.impl;

import com.blisgo.config.TestQueryDslConfig;
import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.repository.ReplyRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@Disabled
@Import(TestQueryDslConfig.class)
class ReplyRepositoryImplTest extends TestRepositoryTemplate {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReplyRepositoryImplTest.class);
    @Autowired
    ReplyRepository replyRepository;

    @BeforeEach
    void setUp() {
        initData();
        entityAssistant(entityAssistOpt.PERSIST, sampleAccount, sampleBoard, sampleReply);
    }

    @AfterEach
    void tearDown() {
        printJaversMonitorResult();
        entityManager.clear();
        entityAssistant(entityAssistOpt.AUTOINCREMEMT, sampleAccount, sampleBoard, sampleReply);
    }

    @Nested
    @DisplayName("content.html")
    class ContentPage {
        @Test
        @DisplayName("댓글 작성 시 작성자의 정보가 기재되어 있는가?")
        void testSelectReplyInnerJoinAccount() {
            List<Reply> result = replyRepository.selectReplyInnerJoinAccount(sampleBoard);
            //diff = javers.compare(sampleReply, result.get(0));
            Assertions.assertNotNull(result);
        }

        @Test
        @DisplayName("댓글 작성, 삭제시 해당 게시글의 댓글 수가 갱신되었는가?")
        void testUpdateReplyCount() {
            boolean result = replyRepository.updateReplyCount(sampleBoard, true);
            Assertions.assertTrue(result);
        }

        @Test
        @DisplayName("댓글이 추가되었는가?")
        void testInsertReply() {
            boolean result = replyRepository.insertReply(sampleReply);
            Assertions.assertTrue(result);
        }

        @Test
        @DisplayName("댓글이 삭제되었는가?")
        void testDeleteReply() {
            boolean result = replyRepository.deleteReply(sampleReply);
            Assertions.assertTrue(result);
        }
    }
}