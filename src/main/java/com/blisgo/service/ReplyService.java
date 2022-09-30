package com.blisgo.service;

import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {
    /**
     * 댓글 불러오는 메서드
     *
     * @param boardDTO 게시글
     * @return 다건
     */
    List<ReplyDTO> findReply(BoardDTO boardDTO);

    /**
     * 댓글 추가하는 메서드
     *
     * @param replyDTO 댓글
     * @param boardDTO 게시글
     * @param accountDTO  사용자 TODO [addReply] 실행 결과 반드시 반환
     */
    void addReply(ReplyDTO replyDTO, BoardDTO boardDTO, AccountDTO accountDTO);

    /**
     * 댓글 삭제 메서드
     *
     * @param replyDTO 댓글
     * @param boardDTO 게시글 TODO [removeReply] 실행 결과 반드시 반환
     */
    void removeReply(ReplyDTO replyDTO, BoardDTO boardDTO);
}
