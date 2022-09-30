package com.blisgo.domain.repository;

import com.blisgo.domain.entity.Board;
import com.blisgo.web.dto.BoardDTO;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface BoardRepository extends Repository<Board, Integer> {
    void insertBoard(Board boardEntity);

    List<BoardDTO> selectBoardList(int index, int limit);

    Board selectBoard(Board boardEntity);

    void deleteBoard(Board boardEntity);

    void updateBoardViews(Board boardEntity);

    void updateBoard(Board boardEntity);

    void updateBoardFavorite(Board boardEntity);

}
