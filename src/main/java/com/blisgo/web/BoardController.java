package com.blisgo.web;

import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.service.BoardService;
import com.blisgo.service.ReplyService;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.ReplyDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/**
 * @author okjae
 */
@RestController
@RequestMapping("board")
public class BoardController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;
    private final ReplyService replyService;
    private final ModelAndView mv = new ModelAndView();
    private static String url;

    public BoardController(BoardService boardService, ReplyService replyService) {
        this.boardService = boardService;
        this.replyService = replyService;
    }

    /**
     * 커뮤니티 게시판
     *
     * @return mv
     */
    @GetMapping
    public ModelAndView community() {
        List<BoardDTO> boards = boardService.findBoards();
        mv.addObject("boards", boards);
        url = RouteUrlHelper.combine(folder.community, page.board);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 게시글 더보기
     *
     * @return mv
     */
    @PostMapping("more")
    public @ResponseBody List<BoardDTO> boardLoadMore() {
        return boardService.findBoardMore();
    }

    /**
     * 게시판 글내용
     *
     * @param boardDTO 게시글
     * @return mv
     */
    @GetMapping("{bdNo}")
    public ModelAndView content(BoardDTO boardDTO) {
        var rs = boardService.findBoard(boardDTO);
        if (rs.isPresent()) {
            boardDTO = rs.get();
        } else {
            log.error("게시글이 조회되지 않았습니다.");
        }
        List<ReplyDTO> replys = replyService.findReply(boardDTO);
        if (!boardService.countBoardViews(boardDTO)) {
            log.warn("게시글 조회수가 올라가지 않음");
        }
        mv.addObject("board", boardDTO);
        mv.addObject("replys", replys);
        url = RouteUrlHelper.combine(folder.community, page.content);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 게시판 글삭제
     *
     * @param boardDTO 게시글
     * @return mv
     */
    @DeleteMapping("{bdNo}")
    public ModelAndView contentDelete(BoardDTO boardDTO) {
        if (boardService.removeBoard(boardDTO)) {
            log.info("삭제 완료, 게시글이 삭제되었습니다.");
        } else {
            log.info("삭제 실패, 게시글이 삭제되지 않았습니다.");
        }
        url = RouteUrlHelper.combine(page.board);

        mv.setView(new RedirectView(url, false));
        return mv;
    }

    /**
     * 게시판 글 수정 화면
     *
     * @param boardDTO 게시글
     * @return mv
     */
    @GetMapping("edit/{bdNo}")
    public ModelAndView contentEdit(BoardDTO boardDTO) {
        var rs = boardService.findBoard(boardDTO);
        if (rs.isPresent()) {
            boardDTO = rs.get();
        } else {
            log.error("편집 글 내용이 조회되지 않았음");
        }

        mv.addObject("board", boardDTO);
        url = RouteUrlHelper.combine(folder.community, page.edit);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 게시판 글 수정 제어
     *
     * @param boardDTO 게시글
     * @return mv
     */
    @PutMapping("edit/{bdNo}")
    public ModelAndView contentEditPut(@Valid BoardDTO boardDTO) {
        if (boardService.modifyBoard(boardDTO)) {
            log.info("수정 완료, 게시글 내용이 변경되었습니다.");
        } else {
            log.error("수정 실패, 게시글 내용이 변경되지 않았습니다.");
        }
        url = RouteUrlHelper.combine(page.board, boardDTO.bdNo());

        mv.setView(new RedirectView(url, false));
        return mv;
    }

    /**
     * 게시글 좋아요 버튼 눌렀을때
     *
     * @param boardDTO 게시글
     * @return mv
     */
    @PutMapping("{bdNo}/like")
    public ModelAndView likeBoard(BoardDTO boardDTO) {
        if (!boardService.countBoardFavorite(boardDTO)) {
            log.error("게시글 좋아요가 수행되지 않음");
        }
        url = RouteUrlHelper.combine(page.board, boardDTO.bdNo());
        mv.setView(new RedirectView(url, false));
        return mv;
    }

    /**
     * 게시판 글작성
     *
     * @return mv
     */
    @GetMapping("write")
    public ModelAndView write() {
        url = RouteUrlHelper.combine(folder.community, page.write);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 게시글 이미지 업로드
     *
     * @param file 파일
     * @return mv
     */
    @PostMapping("write/upload")
    public @ResponseBody String uploadToStorage(MultipartFile file) {
        var rs = boardService.uploadImageToStorage(file);
        if (rs.isPresent()) {
            return rs.get();
        } else {
            log.error("게시글 파일을 업로드하지 못했습니다.");
            return "";
        }
    }

    /**
     * 게시판 글 올리기
     *
     * @param boardDTO  게시글
     * @param principal 인증된 사용자
     * @return mv
     */
    @PostMapping("write")
    public ModelAndView writePOST(@Valid BoardDTO boardDTO, @AuthenticationPrincipal PrincipalDetails principal) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());
        if (boardService.addBoard(boardDTO, accountDTO)) {
            log.info("작성 완료, 글이 작성되었습니다.");
        } else {
            log.error("작성 실패, 글이 작성되지 않았습니다.");
        }

        url = RouteUrlHelper.combine(page.board);

        mv.setView(new RedirectView(url, false));
        return mv;
    }

}
