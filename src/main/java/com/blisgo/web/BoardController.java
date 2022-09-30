package com.blisgo.web;

import com.blisgo.service.BoardService;
import com.blisgo.service.ReplyService;
import com.blisgo.util.CloudinaryUtil;
import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.ReplyDTO;
import com.blisgo.web.dto.AccountDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * @author okjae
 */
@RestController
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;
    private final ModelAndView mv = new ModelAndView();

    CloudinaryUtil cloudinaryUtil;
    String url;

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
        boardDTO = boardService.findBoard(boardDTO);
        List<ReplyDTO> replys = replyService.findReply(boardDTO);
        boardService.countBoardViews(boardDTO);

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
        boardService.removeBoard(boardDTO);

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
        boardDTO = boardService.findBoard(boardDTO);

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
        boardService.modifyBoard(boardDTO);
        url = RouteUrlHelper.combine(page.board, boardDTO.getBdNo());
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
        boardService.countBoardFavorite(boardDTO); // 좋아요 1증가

        url = RouteUrlHelper.combine(page.board, boardDTO.getBdNo());
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
    public @ResponseBody HashMap<String, String> uploadToCloudinary(MultipartFile file) {
        cloudinaryUtil = new CloudinaryUtil();
        String url = cloudinaryUtil.uploadFile(file);
        HashMap<String, String> m = new HashMap<>();
        m.put("link", url);
        return m;
    }

    /**
     * 게시판 글 올리기
     *
     * @param session  세션
     * @param boardDTO 게시글
     * @param accountDTO  사용자
     * @return mv
     */
    @PostMapping("write")
    public ModelAndView writePOST(HttpSession session, @Valid BoardDTO boardDTO, AccountDTO accountDTO) {
        accountDTO = (AccountDTO) session.getAttribute("mem");
        boardService.addBoard(boardDTO, accountDTO);
        url = RouteUrlHelper.combine(page.board);
        mv.setView(new RedirectView(url, false));
        return mv;
    }

}
