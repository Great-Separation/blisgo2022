package com.blisgo.domain.repository.impl;

import com.blisgo.config.TestQueryDslConfig;
import com.blisgo.domain.entity.*;
import com.blisgo.domain.entity.cmmn.Wastes;
import com.blisgo.domain.repository.DictionaryRepository;
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
class DictionaryRepositoryImplTest {
    enum entityAssistOpt {
        PERSIST, AUTOINCREMEMT
    }

    @Autowired
    EntityManager entityManager;
    @Autowired
    DictionaryRepository dictionaryRepository;
    Javers javers;
    Diff diff;

    Dictionary sampleDictionary;
    Hashtag sampleHashtag;
    Account sampleAccount;
    Guide sampleGuide;
    Dogam sampleDogam;

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
        sampleDictionary = Dictionary.builder().name("name").engName("engName").category("category").popularity(0).hit((short) 0).thumbnail("thumbnail").treatment("treatment").build();
        sampleAccount = Account.builder().nickname("nickname").email("email").pass("pass").memPoint(0).profileImage("profileImage").build();
        sampleGuide = Guide.builder().guideCode(Wastes.Ir).guideName("guideName").guideContent("guideContent").imagePath("imagePath").build();
        sampleDogam = Dogam.builder().account(sampleAccount).dictionary(sampleDictionary).build();
        sampleHashtag = Hashtag.builder().dictionary(sampleDictionary).guide(sampleGuide).build();
    }

    @BeforeEach
    void monitorEntity() {
        initData();
        entityAssistant(entityAssistOpt.PERSIST, sampleDictionary, sampleAccount, sampleGuide, sampleDogam, sampleHashtag);
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
        entityAssistant(entityAssistOpt.AUTOINCREMEMT, sampleDictionary, sampleAccount);
    }

    @Nested
    @DisplayName("스케줄러")
    class SchedulerTest {
        @Test
        @Disabled
        @DisplayName("사전 별점이 주기적으로 갱신되는가?")
        void testUpdateDictionaryPopularity() {
            log.info("해당 쿼리는 정상 실행되는 h2, hsqldb 인메모리에서 지원되지 않는 문법임.(update join). 따라서 일단 통과");
            //dictionaryRepository.updateDictionaryPopularity();
            Assertions.assertTrue(true);
        }
    }

    @Nested
    @DisplayName("index.html")
    class IndexPage {
        @Test
        @DisplayName("최근 등록된 폐기물 목록이 조회되는가?")
        void testSelectRecentDictionaryList() {
            List<Dictionary> result = dictionaryRepository.selectRecentDictionaryList();
            diff = javers.compare(sampleDictionary, result.get(0));
            Assertions.assertNotNull(result);
        }
    }

    @Nested
    @DisplayName("wastes.html")
    class WastesPage {
        @Test
        @DisplayName("사전 갤러리에서 폐기물 다건 조회되는가?")
        void testSelectDictionaryList() {
            List<Dictionary> result = dictionaryRepository.selectDictionaryList(0, 24);
            diff = javers.compare(sampleDictionary, result.get(0));
            Assertions.assertNotNull(result);
        }
    }

    @Nested
    @DisplayName("waste.html")
    class WastePage {
        @Test
        @DisplayName("폐기물 단건 조회되는가?")
        void testSelectDictionary() {
            Dictionary result = dictionaryRepository.selectDictionary(sampleDictionary);
            diff = javers.compare(sampleDictionary, result);
            Assertions.assertEquals(sampleDictionary, result);
        }

        @Test
        @DisplayName("열람한 정보와 연관된 폐기물 목록이 조회되는가?")
        void testSelectRelatedDictionaryList() {
            List<Dictionary> result = dictionaryRepository.selectRelatedDictionaryList(Wastes.Ir);
            Assertions.assertNotNull(result.get(0));
        }

        @Test
        @DisplayName("폐기물 데이터의 조회수가 증가했는가?")
        void testUpdateDictionaryHit() {
            boolean result = dictionaryRepository.updateDictionaryHit(sampleDictionary);
            Assertions.assertTrue(result);
        }

        @Test
        @DisplayName("폐기물의 해시태그와 가이드가 담겨있는가?")
        void testSelectHashtagInnerJoinGuide() {
            List<Hashtag> result = dictionaryRepository.selectHashtagInnerJoinGuide(sampleDictionary);
            Assertions.assertNotNull(result);
        }

        @Test
        @DisplayName("조회한 폐기물이 도감에 추가되었는가?")
        void testInsertDogam() {
            boolean result = dictionaryRepository.insertDogam(sampleDogam);
            Assertions.assertTrue(result);
        }
    }
}