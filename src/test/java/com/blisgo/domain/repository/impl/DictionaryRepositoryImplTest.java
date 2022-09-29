package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.*;
import com.blisgo.domain.entity.cmmn.Wastes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
class DictionaryRepositoryImplTest {
    @Mock
    JPAQueryFactory jpaQueryFactory;
    @Mock
    JdbcTemplate jdbcTemplate;
    @Mock
    EntityManager entityManager;
    @InjectMocks
    DictionaryRepositoryImpl dictionaryRepository;

    static Dictionary dictionary;
    @BeforeAll
    static void inputData(){
        dictionary = Dictionary.builder().dicNo(0).engName("test waste").name("테스트 폐기물").thumbnail("").treatment("").build();
    }

    @Before
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSelectRecentDictionaryList() {
        List<Dictionary> result = dictionaryRepository.selectRecentDictionaryList();
        Assertions.assertEquals(List.of(new Dictionary(Integer.valueOf(0), "name", "engName", "category", Integer.valueOf(0), Short.valueOf((short) 0), "thumbnail", "treatment")), result);
    }

    @Test
    void testSelectDictionaryList() {
        List<Dictionary> result = dictionaryRepository.selectDictionaryList(0, 0);
        Assertions.assertEquals(List.of(new Dictionary(Integer.valueOf(0), "name", "engName", "category", Integer.valueOf(0), Short.valueOf((short) 0), "thumbnail", "treatment")), result);
    }

    @Test
    void testProductInfo() {
        Dictionary result = dictionaryRepository.productInfo(new Dictionary(Integer.valueOf(0), null, null, null, null, null, null, null));
        Assertions.assertEquals(new Dictionary(Integer.valueOf(0), null, null, null, null, null, null, null), result);
    }

    @Test
    void testSelectRelatedDictionaryList() {
        List<Dictionary> result = dictionaryRepository.selectRelatedDictionaryList(Wastes.Ir);
        Assertions.assertEquals(List.of(new Dictionary(Integer.valueOf(0), "name", "engName", "category", Integer.valueOf(0), Short.valueOf((short) 0), "thumbnail", "treatment")), result);
    }

    @Test
    void testUpdateDictionaryPopularity() {
        dictionaryRepository.updateDictionaryPopularity();
    }

    @Test
    void testUpdateDictionaryHit() {
        dictionaryRepository.updateDictionaryHit(new Dictionary(Integer.valueOf(0), null, null, null, null, null, null, null));
    }

    @Test
    void testSelectHashtagInnerJoinGuide() {
        List<Hashtag> result = dictionaryRepository.selectHashtagInnerJoinGuide(new Dictionary(Integer.valueOf(0), null, null, null, null, null, null, null));
        Assertions.assertEquals(List.of(new Hashtag(new Dictionary(Integer.valueOf(0), null, null, null, null, null, null, null), new Guide(Wastes.Ir, "guideName", "guideContent", "imagePath"))), result);
    }

    @Test
    void testInsertDogam() {
        boolean result = dictionaryRepository.insertDogam(new Dogam(new User(Integer.valueOf(0), "nickname", "email", "pass", Integer.valueOf(0), "profileImage"), new Dictionary(Integer.valueOf(0), "name", "engName", "category", Integer.valueOf(0), Short.valueOf((short) 0), "thumbnail", "treatment")));
        Assertions.assertEquals(true, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme