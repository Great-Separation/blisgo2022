package com.blisgo.service.impl;

import com.blisgo.domain.entity.Board;
import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.domain.mapper.BoardMapper;
import com.blisgo.domain.repository.BoardRepository;
import com.blisgo.service.BoardService;
import com.blisgo.util.HtmlContentParse;
import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private static int index = 0;
    private static final int limit = 12;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void addBoard(BoardDTO boardDTO, AccountDTO accountDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);

        String boardThumbnail = HtmlContentParse.parseThumbnail(board.getBdContent());
        board = Board.createBoardWithThumbnail(account, board, boardThumbnail);
        boardRepository.insertBoard(board);
    }

    @Override
    public List<BoardDTO> findBoards() {
        String bdContentImgRemoved;
        index = 0;
        List<BoardDTO> board = new ArrayList<>();
        @SuppressWarnings("lint")
        var rs = boardRepository.selectBoardList(index, limit);
        // List<BoardDTO> boardDTOArray = BoardMapper.INSTANCE.toDTOList(rs);
        @SuppressWarnings("lint")
        List<BoardDTO> boardDTOArray = rs;

        for (BoardDTO b : boardDTOArray) {
            bdContentImgRemoved = HtmlContentParse.removeImg(b.getBdContent());
            b = BoardDTO.selectBoardFilterContentImage(b, bdContentImgRemoved);
            board.add(b);
        }

        return board;
    }

    @Override
    public List<BoardDTO> findBoardMore() {
        String bdContentImgRemoved;
        // 더보기
        index += limit;

        List<BoardDTO> board = new ArrayList<>();
        @SuppressWarnings("lint")
        var rs = boardRepository.selectBoardList(index, limit);
        // List<BoardDTO> boardDTOArray = BoardMapper.INSTANCE.toDTOList(rs);
        @SuppressWarnings("lint")
        List<BoardDTO> boardDTOArray = rs;

        for (BoardDTO b : boardDTOArray) {
            bdContentImgRemoved = HtmlContentParse.removeImg(b.getBdContent());
            b = BoardDTO.selectBoardFilterContentImage(b, bdContentImgRemoved);
            board.add(b);
        }

        return board;
    }

    @Override
    public BoardDTO findBoard(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        var rs = boardRepository.selectBoard(board);
        return BoardMapper.INSTANCE.toDTO(rs);
    }

    @Override
    public void removeBoard(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        boardRepository.deleteBoard(board);
    }

    @Override
    public void countBoardViews(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        boardRepository.updateBoardViews(board);
    }

    @Override
    public void modifyBoard(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        boardRepository.updateBoard(board);
    }

    @Override
    public void countBoardFavorite(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        boardRepository.updateBoardFavorite(board);
    }
}
