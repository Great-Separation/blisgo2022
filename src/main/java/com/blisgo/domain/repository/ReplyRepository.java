package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Reply;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReplyRepository extends Repository<Reply, Integer> {
    List<Reply> selectReplyInnerJoinAccount(int bdNo);

    boolean updateReplyCount(int bdNo, boolean isReplied);

    boolean insertReply(Reply replyEntity);

    boolean deleteReply(int replyNo);
}
