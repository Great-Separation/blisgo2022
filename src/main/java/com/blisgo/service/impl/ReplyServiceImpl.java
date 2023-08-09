package com.blisgo.service.impl;

import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.domain.mapper.BoardMapper;
import com.blisgo.domain.mapper.ReplyMapper;
import com.blisgo.domain.repository.ReplyRepository;
import com.blisgo.service.ReplyService;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.ReplyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyServiceImpl(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @Override
    public List<ReplyDTO> findReply(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        var rs = replyRepository.selectReplyInnerJoinAccount(board);
        return ReplyMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public boolean addReply(ReplyDTO replyDTO, BoardDTO boardDTO, AccountDTO accountDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        var reply = ReplyMapper.INSTANCE.toEntity(replyDTO);

        reply = Reply.createReply(reply.getReplyNo(), board, account, reply.getContent());
        return replyRepository.insertReply(reply) & replyRepository.updateReplyCount(board, true);
    }

    @Override
    public boolean removeReply(ReplyDTO replyDTO, BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        var reply = ReplyMapper.INSTANCE.toEntity(replyDTO);
        return replyRepository.deleteReply(reply) & replyRepository.updateReplyCount(board, false);
    }
}
