package com.blisgo.service.impl;

import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.mapper.ReplyMapper;
import com.blisgo.domain.repository.ReplyRepository;
import com.blisgo.service.ReplyService;
import com.blisgo.web.dto.ReplyDTO;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ReplyServiceImpl.class);
    private final ReplyRepository replyRepository;

    public ReplyServiceImpl(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @Override
    public List<ReplyDTO> findReply(int bdNo) {
        return ReplyMapper.INSTANCE.toDTOList(
                replyRepository.selectReplyInnerJoinAccount(bdNo)
        );
    }

    @Override
    public boolean addReply(ReplyDTO replyDTO, int bdNo, int memNo) {
        var reply = ReplyMapper.INSTANCE.toEntity(replyDTO);
        reply = Reply.createReply(reply.getReplyNo(), bdNo, memNo, reply.getContent());

        if (!replyRepository.insertReply(reply)) {
            log.error("댓글이 추가되지 않았습니다");
            return false;
        }
        if (!replyRepository.updateReplyCount(bdNo, true)) {
            log.error("댓글 추가시 조회수가 반영되어야 합니다");
            return false;
        }

        return true;
    }

    @Override
    public boolean removeReply(int replyNo, int bdNo) {
        if (!replyRepository.deleteReply(replyNo)) {
            log.error("댓글이 삭제되지 않았습니다");
            return false;
        }
        if (!replyRepository.updateReplyCount(bdNo, false)) {
            log.error("댓글 삭제시 조회수가 반영되어야 합니다");
            return false;
        }

        return true;
    }
}