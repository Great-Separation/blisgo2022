package com.blisgo.domain.repository.impl;

import com.blisgo.config.TestQueryDslConfig;
import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Board;
import com.blisgo.domain.repository.BoardRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(TestQueryDslConfig.class)
@DisplayName("BoardRepositoryImpl 테스트")
class BoardRepositoryImplTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    public void init(){
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }
    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("Should throw an exception when the board is invalid")
    void insertBoardWhenBoardIsInvalidThenThrowException() {
        Board board = Board.builder().build();
        assertThrows(ConstraintViolationException.class, () -> boardRepository.insertBoard(board));
    }

    @Test
    @DisplayName("Should save the board when the board is valid")
    void insertBoardWhenBoardIsValid() {
        Account account =
                Account.builder()
                        .memNo(1)
                        .nickname("test")
                        .email("test@test.com")
                        .pass("test")
                        .memPoint(0)
                        .profileImage("default_profile_img")
                        .build();
        Board board =
                Board.builder()
                        .bdNo(1)
                        .account(account)
                        .bdTitle("test")
                        .bdCategory("test")
                        .bdContent("test")
                        .bdViews(0)
                        .bdFavorite(0)
                        .bdReplyCount(0)
                        .bdThumbnail("default_thumbnail")
                        .build();

        boardRepository.insertBoard(board);

        assertEquals(board, boardRepository.selectBoard(board));
    }
}