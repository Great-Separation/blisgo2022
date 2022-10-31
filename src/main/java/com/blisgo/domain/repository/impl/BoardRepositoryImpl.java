package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Board;
import com.blisgo.domain.repository.BoardRepository;
import com.blisgo.web.dto.BoardDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.blisgo.domain.entity.QBoard.board;

@Repository
@Transactional
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;

    @Modifying
    @Override
    public boolean insertBoard(Board boardEntity) {
        entityManager.persist(boardEntity);
        return true;
    }

    public List<BoardDTO> selectBoardList(int index, int limit) {

        var tuple = jpaQueryFactory
                .select(board.account.nickname, board.bdNo, board.bdTitle, board.bdContent, board.bdReplyCount,
                        board.bdFavorite, board.bdThumbnail, board.createdDate, board.modifiedDate)
                .from(board).orderBy(board.bdNo.desc()).offset(index).limit(limit).fetch();

        List<BoardDTO> rs = new ArrayList<>();
        for (var row : tuple) {
            Account u = Account.builder().nickname(row.get(board.account.nickname)).build();
            BoardDTO b = BoardDTO.builder().account(u).bdNo(row.get(board.bdNo))
                    .bdTitle(row.get(board.bdTitle)).bdContent(row.get(board.bdContent))
                    .bdReplyCount(row.get(board.bdReplyCount)).bdFavorite(row.get(board.bdFavorite))
                    .bdThumbnail(row.get(board.bdThumbnail)).createdDate(row.get(board.createdDate))
                    .modifiedDate(row.get(board.modifiedDate)).build();
            rs.add(b);
        }

        return rs;
    }

//	@Override
//	public List<Board> selectBoardList(int index, int limit) {
//		return jpaQueryFactory
//				.select(Projections.fields(Board.class, board.account(), board.bdNo, board.bdTitle, board.bdContent,
//						board.bdReplyCount, board.bdFavorite, board.bdThumbnail, board.createdDate, board.modifiedDate))
//				.from(board).orderBy(board.bdNo.desc()).offset(index).limit(limit).fetch();
//
//	}

    @Override
    public Board selectBoard(Board boardEntity) {
        return jpaQueryFactory
                .select(Projections.fields(Board.class, board.account, board.bdNo, board.bdTitle, board.bdContent,
                        board.bdReplyCount, board.bdFavorite, board.bdViews, board.createdDate, board.modifiedDate))
                .from(board).where(board.bdNo.eq(boardEntity.getBdNo())).fetchOne();
    }

    @Modifying
    @Override
    public boolean deleteBoard(Board boardEntity) {
        return jpaQueryFactory.delete(board).where(board.bdNo.eq(boardEntity.getBdNo())).execute() > 0;
    }

    @Modifying
    @Override
    public boolean updateBoardViews(Board boardEntity) {
        return jpaQueryFactory.update(board).set(board.bdViews, board.bdViews.add(1))
                .where(board.bdNo.eq(boardEntity.getBdNo())).execute() > 0;
    }

    @Modifying
    @Override
    public boolean updateBoard(Board boardEntity) {
        return jpaQueryFactory.update(board).set(board.bdTitle, boardEntity.getBdTitle())
                .set(board.bdContent, boardEntity.getBdContent()).where(board.bdNo.eq(boardEntity.getBdNo())).execute() > 0;
    }

    @Modifying
    @Override
    public boolean updateBoardFavorite(Board boardEntity) {
        return jpaQueryFactory.update(board).set(board.bdFavorite, board.bdFavorite.add(1))
                .where(board.bdNo.eq(boardEntity.getBdNo())).execute() > 0;
    }

}
