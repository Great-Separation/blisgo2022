package com.blisgo.service.impl;

import com.blisgo.domain.entity.Board;
import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.domain.mapper.BoardMapper;
import com.blisgo.domain.repository.BoardRepository;
import com.blisgo.exception.GeneralException;
import com.blisgo.service.BoardService;
import com.blisgo.util.CloudinaryUtil;
import com.blisgo.util.HtmlContentParse;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import org.slf4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BoardServiceImpl.class);
    private final BoardRepository boardRepository;
    private static int index = 0;
    private static final int limit = 24;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public boolean addBoard(BoardDTO boardDTO, AccountDTO accountDTO) {
        if (boardRepository.insertBoard(
                Board.createBoardWithThumbnail(
                        AccountMapper.INSTANCE.toEntity(accountDTO),
                        BoardMapper.INSTANCE.toEntity(boardDTO),
                        HtmlContentParse.parseThumbnail(boardDTO.bdContent())))
        ) {
            log.info("작성 완료, 글이 작성되었습니다");
            return true;
        } else {
            log.error("작성 실패, 글이 작성되지 않았습니다");
            return false;
        }
    }

    @Override
    public List<BoardDTO> findBoards() {
        index = 0;
        return boardRepository.selectBoardList(index, limit).stream().map(board ->
                BoardDTO.selectBoardFilterContentImage(
                        board,
                        HtmlContentParse.parseContentPreview(board.bdContent())
                )
        ).toList();
    }

    @Override
    public List<BoardDTO> findBoardMore() {
        index += limit;
        return boardRepository.selectBoardList(index, limit).stream().map(board ->
                BoardDTO.selectBoardFilterContentImage(
                        board,
                        HtmlContentParse.parseContentPreview(board.bdContent())
                )
        ).toList();
    }

    @Override
    public Optional<BoardDTO> findBoard(int bdNo) {
        return Optional.ofNullable(BoardMapper.INSTANCE.toDTO(
                boardRepository.selectBoard(bdNo).orElseThrow(() ->
                        new GeneralException("지정한 글이 없습니다")
                )
        ));
    }

    @Override
    public boolean removeBoard(int bdNo) {
        if (boardRepository.deleteBoard(bdNo) > 0) {
            log.info("삭제 완료, 지정한 게시글이 삭제되었습니다");
            return true;
        } else {
            log.error("삭제 실패, 지정한 게시글이 존재하지 않습니다");
            return false;
        }
    }

    @Override
    public boolean countBoardViews(int bdNo) {
        if (boardRepository.updateBoardViews(bdNo) > 0) {
            log.info("게시글 조회수가 증가했습니다>" + bdNo);
            return true;
        } else {
            log.error("게시글 조회수가 증가하지 않았습니다>" + bdNo);
            return false;
        }
    }

    @Override
    public boolean modifyBoard(BoardDTO boardDTO) {
        Board board = BoardMapper.INSTANCE.toEntity(boardDTO);

        if (boardRepository.updateBoard(board, HtmlContentParse.parseThumbnail(boardDTO.bdContent())) > 0) {
            log.info("지정한 게시글이 성공적으로 변경되었습니다");
            return true;
        } else {
            log.error("지정한 게시글이 없거나 변경되지 않았습니다");
            return false;
        }
    }

    @Override
    public boolean countBoardFavorite(int bdNo) {
        if (boardRepository.updateBoardFavorite(bdNo) == 1) {
            log.info("게시글 좋아요가 수행됨");
            return true;
        } else {
            log.error("게시글 좋아요가 수행되지 않음");
            return false;
        }
    }

    @Override
    public Optional<String> uploadImageToStorage(MultipartFile file) {
        JSONObject jfile = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("success", 1);
            jfile.put("url", new CloudinaryUtil().uploadFile(file, "community"));
            jsonObject.put("file", jfile);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(jsonObject.toString());
    }
}