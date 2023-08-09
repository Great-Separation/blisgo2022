package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.entity.Hashtag;
import com.blisgo.domain.entity.cmmn.Wastes;
import com.blisgo.domain.repository.DictionaryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.blisgo.domain.entity.QDictionary.dictionary;
import static com.blisgo.domain.entity.QGuide.guide;
import static com.blisgo.domain.entity.QHashtag.hashtag;

@Repository
@Transactional
public class DictionaryRepositoryImpl implements DictionaryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    public DictionaryRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Dictionary> selectRecentDictionaryList() {
        return jpaQueryFactory
                .select(Projections.fields(Dictionary.class, dictionary.dicNo, dictionary.name, dictionary.thumbnail))
                .from(dictionary).orderBy(dictionary.dicNo.desc()).limit(12).fetch();
    }

    @Override
    public List<Dictionary> selectDictionaryList(int index, int limit) {
        return jpaQueryFactory
                .select(Projections.fields(Dictionary.class, dictionary.dicNo, dictionary.name, dictionary.thumbnail))
                .from(dictionary).offset(index).limit(limit).fetch();
    }

    @Override
    public Dictionary selectDictionary(Dictionary dictionaryEntity) {
        return jpaQueryFactory.selectFrom(dictionary).where(dictionary.dicNo.eq(dictionaryEntity.getDicNo()))
                .fetchOne();
    }

    @Override
    public List<Dictionary> selectRelatedDictionaryList(List<Wastes> tags) {
        return jpaQueryFactory
                .select(Projections.fields(Dictionary.class, dictionary.dicNo, dictionary.name, dictionary.thumbnail))
                .from(dictionary).where(dictionary.dicNo.in(JPAExpressions.select(hashtag.dictionary.dicNo).from(hashtag)
                        .where(hashtag.guide.guideCode.in(tags))
                )).orderBy(Expressions.numberTemplate(Integer.class, "function('rand')").asc()).limit(4).fetch();
    }

    @Modifying
    @Override
    public boolean updateDictionaryPopularity() {
        String sql = "UPDATE dictionary JOIN (SELECT dic_no, NTILE(10) OVER (ORDER BY hit) AS star FROM dictionary) AS d2 SET popularity = d2.star WHERE dictionary.dic_no = d2.dic_no";
        return jdbcTemplate.update(sql) > 0;
    }

    @Modifying
    @Override
    public boolean updateDictionaryHit(Dictionary dictionaryEntity) {
        return jpaQueryFactory.update(dictionary).set(dictionary.hit, dictionary.hit.add(1))
                .where(dictionary.dicNo.eq(dictionaryEntity.getDicNo())).execute() > 0;
    }

    @Override
    public List<Hashtag> selectHashtagInnerJoinGuide(Dictionary dictionaryEntity) {
        return jpaQueryFactory
                .select(Projections.fields(Hashtag.class, hashtag.guide))
                .from(hashtag).innerJoin(guide).on(hashtag.guide.guideCode.eq(guide.guideCode))
                .where(hashtag.dictionary.dicNo.eq(dictionaryEntity.getDicNo())).fetch();
    }

    @Modifying
    @Override
    public boolean insertDogam(Dogam dogamEntity) {
        try {
            entityManager.merge(dogamEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}