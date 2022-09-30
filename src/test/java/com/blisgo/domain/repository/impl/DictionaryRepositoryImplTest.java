package com.blisgo.domain.repository.impl;

import com.blisgo.config.TestQueryDslConfig;
import com.blisgo.domain.entity.*;
import com.blisgo.domain.entity.cmmn.Wastes;
import com.blisgo.domain.repository.DictionaryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
@Import(TestQueryDslConfig.class)
class DictionaryRepositoryImplTest {
    @Autowired
    JPAQueryFactory jpaQueryFactory;
    @Autowired
    EntityManager entityManager;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DictionaryRepository dictionaryRepository;
    Javers javers;
    Dictionary expected;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        expected = Dictionary.builder().name("names").engName("sibal").category("category").popularity(0).hit((short) 0).thumbnail("thumbnail").treatment("treatment").build();
        entityManager.persist(expected);
        javers = JaversBuilder.javers().withListCompareAlgorithm(org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE).build();
    }

    @AfterEach
    void clearAutoIncrement() {
        entityManager.createNativeQuery("TRUNCATE TABLE dictionary").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE dictionary ALTER COLUMN dic_no RESTART WITH 1").executeUpdate();
    }

    @Test
    void testSelectRecentDictionaryList() {
        List<Dictionary> result = dictionaryRepository.selectRecentDictionaryList();
        System.out.println("이름>"+result.get(0).getName());
        System.out.println("영어이름>"+result.get(0).getEngName());
//dictionary.dicNo, dictionary.name, dictionary.thumbnail
        Diff diff = javers.compare(expected, result.get(0));

        System.out.println("iterating over changes:");
        diff.getChanges().forEach(change -> System.out.println("- " + change));

        Assertions.assertTrue(diff.getChanges().equals(0));
        //Assertions.assertEquals(List.of(new Dictionary(0, "name", "engName", "category", 0, (short) 0, "thumbnail", "treatment")), result);
    }

    @Test
    void testSelectDictionaryList() {
        List<Dictionary> result = dictionaryRepository.selectDictionaryList(0, 24);
        Assertions.assertEquals(List.of(new Dictionary(1, "name", "engName", "category", 0, (short) 0, "thumbnail", "treatment")), result);
    }

    @Test
    void testProductInfo() {
        Dictionary result = dictionaryRepository.productInfo(new Dictionary(0, null, null, null, null, null, null, null));
        Assertions.assertEquals(new Dictionary(1, null, null, null, null, null, null, null), result);
    }

    @Test
    void testSelectRelatedDictionaryList() {
        List<Dictionary> result = dictionaryRepository.selectRelatedDictionaryList(Wastes.Ir);
        Assertions.assertEquals(List.of(new Dictionary(1, "name", "engName", "category", 0, (short) 0, "thumbnail", "treatment")), result);
    }

    @Test
    void testUpdateDictionaryPopularity() {
        dictionaryRepository.updateDictionaryPopularity();
    }

    @Test
    void testUpdateDictionaryHit() {
        dictionaryRepository.updateDictionaryHit(new Dictionary(1, null, null, null, null, null, null, null));
    }

    @Test
    void testSelectHashtagInnerJoinGuide() {
        List<Hashtag> result = dictionaryRepository.selectHashtagInnerJoinGuide(new Dictionary(1, null, null, null, null, null, null, null));
        Assertions.assertEquals(List.of(new Hashtag(new Dictionary(1, null, null, null, null, null, null, null), new Guide(Wastes.Ir, "guideName", "guideContent", "imagePath"))), result);
    }

    @Test
    void testInsertDogam() {
        boolean result = dictionaryRepository.insertDogam(new Dogam(new Account(1, "nickname", "email", "pass", 0, "profileImage"), new Dictionary(1, "name", "engName", "category", 0, (short) 0, "thumbnail", "treatment")));
        Assertions.assertTrue(result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme