package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.Board;
import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.repository.ReplyRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.blisgo.domain.entity.QBoard.board;
import static com.blisgo.domain.entity.QReply.reply;
import static com.blisgo.domain.entity.QUser.user;

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
    public List<Reply> selectReplyInnerJoinUser(Board boardEntity) {
        return jpaQueryFactory
                .select(Projections.fields(Reply.class, reply.user,
                        reply.createdDate, reply.content))
                .from(reply).innerJoin(user).on(reply.user.memNo.eq(user.memNo))
                .where(reply.board.bdNo.eq(boardEntity.getBdNo())).fetch();
    }

    @Modifying
    @Override
    public void updateReplyCount(Board boardEntity, boolean flag) {
        if (flag) {
            jpaQueryFactory.update(board).set(board.bdReplyCount, board.bdReplyCount.add(1))
                    .where(board.bdNo.eq(boardEntity.getBdNo())).execute();
        } else {
            jpaQueryFactory.update(board).set(board.bdReplyCount, board.bdReplyCount.subtract(1))
                    .where(board.bdNo.eq(boardEntity.getBdNo())).execute();
        }

    }

    @Modifying
    @Override
    public void insertReply(Reply replyEntity) {
        entityManager.persist(replyEntity);

    }

    @Modifying
    @Override
    public void deleteReply(Reply replyEntity) {
        jpaQueryFactory.delete(reply).where(reply.replyNo.eq(replyEntity.getReplyNo())).execute();

    }

}
