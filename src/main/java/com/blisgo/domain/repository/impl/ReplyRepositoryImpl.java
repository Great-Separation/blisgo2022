package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.Board;
import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.repository.ReplyRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.blisgo.domain.entity.QAccount.account;
import static com.blisgo.domain.entity.QBoard.board;
import static com.blisgo.domain.entity.QReply.reply;

@Repository
@Transactional
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;

    @Override
    public List<Reply> selectReplyInnerJoinAccount(Board boardEntity) {
        return jpaQueryFactory
                .select(Projections.fields(Reply.class, reply.replyNo, reply.account,
                        reply.createdDate, reply.content))
                .from(reply).innerJoin(account).on(reply.account.memNo.eq(account.memNo))
                .where(reply.board.bdNo.eq(boardEntity.getBdNo())).fetch();
    }

    @Modifying
    @Override
    public boolean updateReplyCount(Board boardEntity, boolean isReplied) {
        return jpaQueryFactory.update(board).set(board.bdReplyCount, isReplied ? board.bdReplyCount.add(1) : board.bdReplyCount.subtract(1))
                .where(board.bdNo.eq(boardEntity.getBdNo())).execute() > 0;
    }

    @Modifying
    @Override
    public boolean insertReply(Reply replyEntity) {
        entityManager.persist(replyEntity);
        return true;
    }

    @Modifying
    @Override
    public boolean deleteReply(Reply replyEntity) {
        return jpaQueryFactory.delete(reply).where(reply.replyNo.eq(replyEntity.getReplyNo())).execute() > 0;
    }

}
