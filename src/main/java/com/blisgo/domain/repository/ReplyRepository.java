package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Board;
import com.blisgo.domain.entity.Reply;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReplyRepository extends Repository<Reply, Integer> {
    List<Reply> selectReplyInnerJoinAccount(Board boardEntity);

    boolean updateReplyCount(Board boardEntity, boolean isReplied);

    boolean insertReply(Reply replyEntity);

    boolean deleteReply(Reply replyEntity);
}
