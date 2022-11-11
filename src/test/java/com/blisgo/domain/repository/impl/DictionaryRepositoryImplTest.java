package com.blisgo.domain.repository.impl;

import com.blisgo.config.TestQueryDslConfig;
import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Hashtag;
import com.blisgo.domain.entity.cmmn.Wastes;
import com.blisgo.domain.repository.DictionaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

@Slf4j
@DataJpaTest
@Import(TestQueryDslConfig.class)
class DictionaryRepositoryImplTest extends TestRepositoryTemplate {

    @Autowired
    DictionaryRepository dictionaryRepository;

    @BeforeEach
    void setUp() {
        initData();
        entityAssistant(entityAssistOpt.PERSIST, sampleDictionary, sampleAccount, sampleGuide, sampleDogam, sampleHashtag);
    }

    @AfterEach
    void tearDown() {
        printJaversMonitorResult();
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
            Assertions.assertFalse(diff.hasChanges());
        }

        @Test
        @DisplayName("열람한 정보와 연관된 폐기물 목록이 조회되는가?")
        void testSelectRelatedDictionaryList() {
            List<Wastes> tags = Arrays.asList(Wastes.Ir, Wastes.NF);
            List<Dictionary> result = dictionaryRepository.selectRelatedDictionaryList(tags);
            diff = javers.compare(sampleDictionary, result.get(0));
            Assertions.assertNotNull(result);
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
            diff = javers.compare(sampleHashtag, result.get(0));
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