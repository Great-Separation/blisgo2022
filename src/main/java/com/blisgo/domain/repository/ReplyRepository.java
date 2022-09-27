package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Board;
import com.blisgo.domain.entity.Reply;

import java.util.List;

public interface ReplyRepository {
    List<Reply> selectReplyInnerJoinUser(Board boardEntity);

    void updateReplyCount(Board boardEntity, boolean flag);

    void insertReply(Reply replyEntity);

    void deleteReply(Reply replyEntity);
}
