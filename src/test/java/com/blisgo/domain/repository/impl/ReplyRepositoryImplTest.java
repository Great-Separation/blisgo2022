package com.blisgo.domain.repository.impl;

import com.blisgo.config.TestQueryDslConfig;
import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Board;
import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.repository.ReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@DataJpaTest
@Import(TestQueryDslConfig.class)
class ReplyRepositoryImplTest {
    enum entityAssistOpt {
        PERSIST, AUTOINCREMEMT
    }

    @Autowired
    EntityManager entityManager;
    @Autowired
    ReplyRepository replyRepository;
    Javers javers;
    Diff diff;

    Account sampleAccount;
    Board sampleBoard;
    Reply sampleReply;

    private void entityAssistant(@NotNull entityAssistOpt opt, Object... entities) {
        switch (opt) {
            case PERSIST:
                for (var e : entities)
                    entityManager.persist(e);
                break;

            case AUTOINCREMEMT:
                for (var e : entities)
                    entityManager.createNativeQuery(String.format("ALTER TABLE %s AUTO_INCREMENT = 1", e.getClass().getSimpleName())).executeUpdate();
                break;

            default:
                break;
        }
    }

    private void initData() {
        sampleAccount = Account.builder().nickname("nickname").email("email").pass("pass").memPoint(0).profileImage("profileImage").build();
        sampleBoard = Board.builder().account(sampleAccount).bdTitle("bdTitle").bdCategory("bdCategory").bdContent("bdContent").bdViews(0).bdFavorite(0).bdReplyCount(0).bdThumbnail("bdThumbnail").build();
        sampleReply = Reply.builder().board(sampleBoard).account(sampleAccount).content("content").build();
    }

    @BeforeEach
    void monitorEntity() {
        initData();
        entityAssistant(entityAssistOpt.PERSIST, sampleAccount, sampleBoard, sampleReply);
        javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE).build();
    }

    @AfterEach
    void manageEntity() {
        if (diff != null) {
            System.out.println("감사 결과>" + diff.changesSummary());
            diff.getChanges().forEach(change -> System.out.println("- " + change));
        } else {
            log.info("해당 테스트에서는 엔티티 감사 안했음");
        }
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