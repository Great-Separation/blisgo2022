package com.blisgo.service;

import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.AccountDTO;

import java.util.List;

public interface BoardService {

    /**
     * 글 등록 메서드
     *
     * @param boardDTO 게시글
     * @param accountDTO  사용자 TODO [addBoard] 실행 결과 반드시 반환
     */
    void addBoard(BoardDTO boardDTO, AccountDTO accountDTO);

    /**
     * 글 목록을 가지고 오는 메서드(페이징 없을때)
     *
     * @return 다건
     */
    List<BoardDTO> findBoards();

    /**
     * 글 더보기 메서드
     *
     * @return 다건
     */
    List<BoardDTO> findBoardMore();

    /**
     * 글 상세보기 요청을 처리할 메서드
     *
     * @param boardDTO 게시글
     * @return 단건
     */
    BoardDTO findBoard(BoardDTO boardDTO);

    /**
     * 글 삭제 요청을 처리할 메서드
     *
     * @param boardDTO 게시글 TODO [removeBoard] 실행 결과 반드시 반환
     */
    void removeBoard(BoardDTO boardDTO);

    /**
     * DB에 있는 view의 값을 증가 시켜주는 메서드
     *
     * @param boardDTO 게시글 TODO [countBoardViews] 실행 결과 반드시 반환
     */
    void countBoardViews(BoardDTO boardDTO);

    /**
     * 글 수정 메서드
     *
     * @param boardDTO 게시글 TODO [modifyBoard] 실행 결과 반드시 반환
     */
    void modifyBoard(BoardDTO boardDTO);

    /**
     * 좋아요 +1
     *
     * @param boardDTO 게시글 TODO [countBoardFavorite] 실행 결과 반드시 반환
     */
    void countBoardFavorite(BoardDTO boardDTO);
}
