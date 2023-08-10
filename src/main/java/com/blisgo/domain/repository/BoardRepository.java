package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Board;
import com.blisgo.web.dto.BoardDTO;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends Repository<Board, Long> {
    boolean insertBoard(Board boardEntity);

    List<BoardDTO> selectBoardList(int index, int limit);

    Optional<Board> selectBoard(int bdNo);

    long deleteBoard(int bdNo);

    long updateBoardViews(int boardEntity);

    long updateBoard(Board boardEntity, String boardThumbnail);

    long updateBoardFavorite(int bdNo);

}
