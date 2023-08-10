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
    private static final int limit = 12;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void addBoard(BoardDTO boardDTO, AccountDTO accountDTO) {
        if (boardRepository.insertBoard(
                Board.createBoardWithThumbnail(
                        AccountMapper.INSTANCE.toEntity(accountDTO),
                        BoardMapper.INSTANCE.toEntity(boardDTO),
                        HtmlContentParse.parseThumbnail(boardDTO.bdContent())))
        ) {
            log.info("작성 완료, 글이 작성되었습니다");
        } else {
            log.error("작성 실패, 글이 작성되지 않았습니다");
        }
    }

    @Override
    public List<BoardDTO> findBoards() {
        index = 0;

        return boardRepository.selectBoardList(index, limit).stream().map(board -> {
            String bdContentImgRemoved = HtmlContentParse.parseContentPreview(board.bdContent());
            return BoardDTO.selectBoardFilterContentImage(board, bdContentImgRemoved);
        }).toList();
    }

    @Override
    public List<BoardDTO> findBoardMore() {
        // 더보기
        index += limit;

        return boardRepository.selectBoardList(index, limit).stream().map(board -> {
            String bdContentImgRemoved = HtmlContentParse.parseContentPreview(board.bdContent());
            return BoardDTO.selectBoardFilterContentImage(board, bdContentImgRemoved);
        }).toList();
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
    public void removeBoard(int bdNo) {
        if (boardRepository.deleteBoard(bdNo) > 0) {
            log.info("삭제 완료, 지정한 게시글이 삭제되었습니다");
        } else {
            log.error("삭제 실패, 지정한 게시글이 존재하지 않습니다");
        }
    }

    @Override
    public void countBoardViews(int bdNo) {
        if (boardRepository.updateBoardViews(bdNo) > 0) {
            log.info("게시글 조회수가 증가했습니다>" + bdNo);
        } else {
            log.error("게시글 조회수가 증가하지 않았습니다>" + bdNo);
        }
    }

    @Override
    public void modifyBoard(BoardDTO boardDTO) {
        Board board = BoardMapper.INSTANCE.toEntity(boardDTO);

        if (boardRepository.updateBoard(board, HtmlContentParse.parseThumbnail(boardDTO.bdContent())) > 0) {
            log.info("지정한 게시글이 성공적으로 변경되었습니다");
        } else {
            log.error("지정한 게시글이 없거나 변경되지 않았습니다");
        }
    }

    @Override
    public void countBoardFavorite(int bdNo) {
        if (boardRepository.updateBoardFavorite(bdNo) == 1) {
            log.info("게시글 좋아요가 수행됨");
        } else {
            log.error("게시글 좋아요가 수행되지 않음");
        }
    }

    @Override
    public Optional<String> uploadImageToStorage(MultipartFile file) {
        CloudinaryUtil cloudinaryUtil = new CloudinaryUtil();
        String url = cloudinaryUtil.uploadFile(file, "community");
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