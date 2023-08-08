package com.blisgo.web;

import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.service.ReplyService;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.BoardDTO;
import com.blisgo.web.dto.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("reply")
public class ReplyController {
    private final ReplyService replyService;
    private final ModelAndView mv = new ModelAndView();
    String url;

    /**
     * 댓글 작성
     *
     * @param principal 인증된 사용자
     * @param boardDTO  게시글
     * @param replyDTO  댓글
     * @return mv
     */
    @PostMapping("{bdNo}")
    public ModelAndView replyPOST(@AuthenticationPrincipal PrincipalDetails principal, BoardDTO boardDTO, @Valid ReplyDTO replyDTO) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());
        if (!replyService.addReply(replyDTO, boardDTO, accountDTO)) {
            log.error("댓글이 작성되지 않았습니다.");
        }
        url = RouteUrlHelper.combine(page.board, boardDTO.getBdNo());
        mv.setView(new RedirectView(url, false));
        return mv;
    }

    /**
     * 댓글 삭제
     *
     * @param boardDTO 게시글
     * @param replyDTO 댓글
     * @return mv
     */
    @DeleteMapping("{bdNo}/{replyNo}")
    public ModelAndView replyRemove(BoardDTO boardDTO, ReplyDTO replyDTO) {
        if (!replyService.removeReply(replyDTO, boardDTO)) {
            log.error("댓글이 삭제되지 않았습니다.");
        }
        url = RouteUrlHelper.combine(page.board, boardDTO.getBdNo());
        mv.setView(new RedirectView(url, false));
        return mv;
    }
}
