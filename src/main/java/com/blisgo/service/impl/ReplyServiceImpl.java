package com.blisgo.service.impl;

import com.blisgo.domain.entity.Reply;
import com.blisgo.domain.mapper.BoardMapper;
import com.blisgo.domain.mapper.ReplyMapper;
import com.blisgo.domain.mapper.UserMapper;
import com.blisgo.domain.repository.ReplyRepository;
import com.blisgo.service.ReplyService;
import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.ReplyDTO;
import com.blisgo.web.dto.UserDTO;
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
        var rs = replyRepository.selectReplyInnerJoinUser(board);
        return ReplyMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public void addReply(ReplyDTO replyDTO, BoardDTO boardDTO, UserDTO userDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        var user = UserMapper.INSTANCE.toEntity(userDTO);
        var reply = ReplyMapper.INSTANCE.toEntity(replyDTO);

        reply = Reply.createReply(reply.getReplyNo(), board, user, reply.getContent());

        replyRepository.insertReply(reply);
        replyRepository.updateReplyCount(board, true);
    }

    @Override
    public void removeReply(ReplyDTO replyDTO, BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        var reply = ReplyMapper.INSTANCE.toEntity(replyDTO);
        replyRepository.deleteReply(reply);
        replyRepository.updateReplyCount(board, false);
    }
}
