package com.blisgo.service.impl;

import com.blisgo.domain.entity.Board;
import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.domain.mapper.BoardMapper;
import com.blisgo.domain.repository.BoardRepository;
import com.blisgo.service.BoardService;
import com.blisgo.util.CloudinaryUtil;
import com.blisgo.util.HtmlContentParse;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    CloudinaryUtil cloudinaryUtil;
    private static int index = 0;
    private static final int limit = 12;

    @Override
    public boolean addBoard(BoardDTO boardDTO, AccountDTO accountDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);

        String boardThumbnail = HtmlContentParse.parseThumbnail(board.getBdContent());
        board = Board.createBoardWithThumbnail(account, board, boardThumbnail);
        return boardRepository.insertBoard(board);
    }

    @Override
    public List<BoardDTO> findBoards() {
        Optional<String> bdContentImgRemoved;
        index = 0;
        List<BoardDTO> board = new ArrayList<>();
        var rs = boardRepository.selectBoardList(index, limit);

        for (BoardDTO b : rs) {
            bdContentImgRemoved = Optional.ofNullable(HtmlContentParse.parseContentPreview(b.getBdContent()));
            if (bdContentImgRemoved.isPresent()) {
                b = BoardDTO.selectBoardFilterContentImage(b, bdContentImgRemoved.get());
            } else {
                b = BoardDTO.selectBoardFilterContentImage(b, null);
            }
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

        var rs = boardRepository.selectBoardList(index, limit);

        for (BoardDTO b : rs) {
            bdContentImgRemoved = HtmlContentParse.parseContentPreview(b.getBdContent());
            b = BoardDTO.selectBoardFilterContentImage(b, bdContentImgRemoved);
            board.add(b);
        }

        return board;
    }

    @Override
    public Optional<BoardDTO> findBoard(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        var rs = boardRepository.selectBoard(board);
        return Optional.ofNullable(BoardMapper.INSTANCE.toDTO(rs));
    }

    @Override
    public boolean removeBoard(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        return boardRepository.deleteBoard(board);
    }

    @Override
    public boolean countBoardViews(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        return boardRepository.updateBoardViews(board);
    }

    @Override
    public boolean modifyBoard(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        return boardRepository.updateBoard(board);
    }

    @Override
    public boolean countBoardFavorite(BoardDTO boardDTO) {
        var board = BoardMapper.INSTANCE.toEntity(boardDTO);
        return boardRepository.updateBoardFavorite(board);
    }

    @Override
    public Optional<String> uploadImageToStorage(MultipartFile file) {
        cloudinaryUtil = new CloudinaryUtil();
        String url = cloudinaryUtil.uploadFile(file, "board");
        url = cloudinaryUtil.addOpt(url, "q_auto,f_jpg/");
        JSONObject jfile = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("success", 1);
            jfile.put("url", url);
            jsonObject.put("file", jfile);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(jsonObject.toString());
    }
}
