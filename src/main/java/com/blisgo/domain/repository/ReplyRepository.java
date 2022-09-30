package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Board;
import com.blisgo.domain.entity.Reply;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReplyRepository extends Repository<Reply, Integer> {
    List<Reply> selectReplyInnerJoinUser(Board boardEntity);

    void updateReplyCount(Board boardEntity, boolean flag);

    void insertReply(Reply replyEntity);

    void deleteReply(Reply replyEntity);
}
