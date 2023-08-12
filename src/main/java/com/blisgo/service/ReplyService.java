package com.blisgo.service;

import com.blisgo.web.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {
    /**
     * 댓글 불러오는 메서드
     *
     * @param boardDTO 게시글
     * @return 다건
     */
    List<ReplyDTO> findReply(int boardDTO);

    /**
     * 댓글 추가하는 메서드
     *
     * @param replyDTO 댓글
     * @param bdNo     게시글
     * @param memNo    사용자
     */
    boolean addReply(ReplyDTO replyDTO, int bdNo, int memNo);

    /**
     * 댓글 삭제 메서드
     *
     * @param replyNo 댓글
     * @param bdNo    게시글 번호
     */
    boolean removeReply(int replyNo, int bdNo);
}
