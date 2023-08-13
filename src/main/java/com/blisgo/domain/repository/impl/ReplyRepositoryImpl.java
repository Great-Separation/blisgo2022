package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.repository.ReplyRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.blisgo.domain.entity.QAccount.account;
import static com.blisgo.domain.entity.QBoard.board;
import static com.blisgo.domain.entity.QReply.reply;

@Repository
@Transactional
public class ReplyRepositoryImpl implements ReplyRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;

    public ReplyRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager entityManager) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }

    @Override
    public List<Reply> selectReplyInnerJoinAccount(int bdNo) {
        return jpaQueryFactory
                .select(Projections.fields(Reply.class, reply.replyNo, Projections.fields(Account.class, account.nickname, account.profileImage).as("account"),
                        reply.createdDate, reply.content))
                .from(reply).innerJoin(account).on(reply.account.memNo.eq(account.memNo))
                .where(reply.board.bdNo.eq(bdNo)).fetch();
    }

    @Modifying
    @Override
    public boolean updateReplyCount(int bdNo, boolean isReplied) {
        return jpaQueryFactory.update(board).set(board.bdReplyCount, isReplied ? board.bdReplyCount.add(1) : board.bdReplyCount.subtract(1))
                .where(board.bdNo.eq(bdNo)).execute() > 0;
    }

    @Modifying
    @Override
    public boolean insertReply(Reply replyEntity) {
        entityManager.persist(replyEntity);
        return true;
    }

    @Modifying
    @Override
    public boolean deleteReply(int replyNo) {
        return jpaQueryFactory.delete(reply).where(reply.replyNo.eq(replyNo)).execute() > 0;
    }

}