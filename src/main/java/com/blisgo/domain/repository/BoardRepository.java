package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Board;
import com.blisgo.web.dto.BoardDTO;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface BoardRepository extends Repository<Board, Integer> {
    boolean insertBoard(Board boardEntity);

    List<BoardDTO> selectBoardList(int index, int limit);

    Board selectBoard(Board boardEntity);

    boolean deleteBoard(Board boardEntity);

    boolean updateBoardViews(Board boardEntity);

    boolean updateBoard(Board boardEntity, String boardThumbnail);

    boolean updateBoardFavorite(Board boardEntity);

}
