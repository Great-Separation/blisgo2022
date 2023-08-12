package com.blisgo.web.controller;

import com.blisgo.constant.Folder;
import com.blisgo.constant.Page;
import com.blisgo.util.RouteUrlHelper;
import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.exception.GeneralException;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.service.BoardService;
import com.blisgo.service.ReplyService;
import com.blisgo.web.dto.BoardDTO;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("board")
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

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
        return new ModelAndView(
                RouteUrlHelper.combine(Folder.community, Page.board),
                Map.of("boards", boardService.findBoards())
        );
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
     * @param bdNo 게시글
     * @return mv
     */
    @GetMapping("{bdNo}")
    public ModelAndView content(@PathVariable final int bdNo) {
        boardService.countBoardViews(bdNo);

        BoardDTO boardDTO = boardService.findBoard(bdNo).orElseThrow(() ->
                new GeneralException("해당 게시글이 조회되지 않습니다")
        );

        return new ModelAndView(
                RouteUrlHelper.combine(Folder.community, Page.content),
                Map.of(
                        "board", boardDTO,
                        "replys", replyService.findReply(bdNo)
                )
        );
    }

    /**
     * 게시판 글삭제
     *
     * @param bdNo 게시글
     * @return mv
     */
    @DeleteMapping("{bdNo}")
    public ModelAndView contentDelete(@PathVariable final int bdNo) {
        boardService.removeBoard(bdNo);

        return new ModelAndView(
                new RedirectView(RouteUrlHelper.combine(Page.board), false)
        );
    }

    /**
     * 게시판 글 수정 화면
     *
     * @param bdNo 게시글
     * @return mv
     */
    @GetMapping("edit/{bdNo}")
    public ModelAndView contentEdit(@PathVariable final int bdNo) {
        BoardDTO boardDTO = boardService.findBoard(bdNo).orElseThrow(() ->
                new GeneralException("편집 글 내용이 조회되지 않았음")
        );

        return new ModelAndView(
                RouteUrlHelper.combine(Folder.community, Page.edit),
                Map.of("board", boardDTO)
        );
    }

    /**
     * 게시판 글 수정 제어
     *
     * @param boardDTO 게시글
     * @return mv
     */
    @PatchMapping("edit/{bdNo}")
    public ModelAndView contentEdit(@Valid final BoardDTO boardDTO) {
        // todo 수정 완료되었다고 알려주고 새로 고침 없이 프론트 단에 알림만 띄우기
        boardService.modifyBoard(boardDTO);

        return new ModelAndView(
                new RedirectView(RouteUrlHelper.combine(Page.board, boardDTO.bdNo()))
        );
    }

    /**
     * 게시글 좋아요 버튼 눌렀을때
     *
     * @param bdNo 게시글
     * @return mv
     */
    @PutMapping("{bdNo}/like")
    public ModelAndView likeBoard(@PathVariable final int bdNo) {
        boardService.countBoardFavorite(bdNo);

        return new ModelAndView(
                new RedirectView(RouteUrlHelper.combine(Page.board, bdNo), false)
        );
    }

    /**
     * 게시판 글작성
     *
     * @return mv
     */
    @GetMapping("write")
    public ModelAndView write() {
        return new ModelAndView(
                RouteUrlHelper.combine(Folder.community, Page.write)
        );
    }

    /**
     * 게시글 이미지 업로드
     *
     * @param file 파일
     * @return mv
     */
    @PostMapping("write/upload")
    public @ResponseBody String uploadToStorage(final MultipartFile file) {
        return boardService.uploadImageToStorage(file).orElseThrow(() ->
                new GeneralException("게시글 파일을 업로드하지 못했습니다")
        );
    }

    /**
     * 게시판 글 올리기
     *
     * @param boardDTO  게시글
     * @param principal 인증된 사용자
     * @return mv
     */
    @PostMapping("write")
    public ModelAndView writePOST(
            @Valid final BoardDTO boardDTO,
            @AuthenticationPrincipal PrincipalDetails principal
    ) {
        boardService.addBoard(
                boardDTO,
                AccountMapper.INSTANCE.toDTO(principal.getAccount())
        );

        return new ModelAndView(
                new RedirectView(RouteUrlHelper.combine(Page.board), false)
        );
    }
}