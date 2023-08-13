package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Board;
import com.blisgo.domain.repository.BoardRepository;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.AccountDTOBuilder;
import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.BoardDTOBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blisgo.domain.entity.QAccount.account;
import static com.blisgo.domain.entity.QBoard.board;

@Repository
@Transactional
public class BoardRepositoryImpl implements BoardRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;

    public BoardRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }

    @Modifying
    @Override
    public boolean insertBoard(Board boardEntity) {
        try {
            entityManager.persist(boardEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // TODO 코드 줄이기
    public List<BoardDTO> selectBoardList(int index, int limit) {

        var tuple = jpaQueryFactory
                .select(board.account.nickname, board.bdNo, board.bdTitle, board.bdContent, board.bdReplyCount,
                        board.bdFavorite, board.bdThumbnail, board.createdDate, board.modifiedDate)
                .from(board).orderBy(board.bdNo.desc()).offset(index).limit(limit).fetch();

        List<BoardDTO> rs = new ArrayList<>();
        for (var row : tuple) {
            AccountDTO u = AccountDTOBuilder.builder().nickname(row.get(board.account.nickname)).build();
            BoardDTO b = BoardDTOBuilder.builder().account(u).bdNo(row.get(board.bdNo))
                    .bdTitle(row.get(board.bdTitle)).bdContent(row.get(board.bdContent))
                    .bdReplyCount(row.get(board.bdReplyCount)).bdFavorite(row.get(board.bdFavorite))
                    .bdThumbnail(row.get(board.bdThumbnail)).createdDate(row.get(board.createdDate))
                    .modifiedDate(row.get(board.modifiedDate)).build();
            rs.add(b);
        }

        return rs;
    }

    @Override
    public Optional<Board> selectBoard(int bdNo) {
        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.fields(Board.class, Projections.fields(Account.class, account.nickname, account.profileImage).as("account"), board.bdNo, board.bdTitle, board.bdContent,
                        board.bdReplyCount, board.bdFavorite, board.bdViews, board.createdDate, board.modifiedDate))
                .from(board).where(board.bdNo.eq(bdNo)).fetchOne());
    }

    @Modifying
    @Override
    public long deleteBoard(int bdNo) {
        return jpaQueryFactory.delete(board).where(board.bdNo.eq(bdNo)).execute();
    }

    @Modifying
    @Override
    public long updateBoardViews(int bdNo) {
        return jpaQueryFactory.update(board).set(board.bdViews, board.bdViews.add(1))
                .where(board.bdNo.eq(bdNo)).execute();
    }

    @Modifying
    @Override
    public long updateBoard(Board boardEntity, String boardThumbnail) {
        return jpaQueryFactory.update(board).set(board.bdTitle, boardEntity.getBdTitle())
                .set(board.bdContent, boardEntity.getBdContent()).set(board.bdThumbnail, boardThumbnail).where(board.bdNo.eq(boardEntity.getBdNo())).execute();
    }

    @Modifying
    @Override
    public long updateBoardFavorite(int bdNo) {
        return jpaQueryFactory.update(board).set(board.bdFavorite, board.bdFavorite.add(1))
                .where(board.bdNo.eq(bdNo)).execute();
    }

}