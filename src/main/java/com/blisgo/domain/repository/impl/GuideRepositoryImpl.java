package com.blisgo.domain.repository.impl;

import com.blisgo.domain.repository.GuideRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class GuideRepositoryImpl implements GuideRepository {

    public GuideRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
    }

}
