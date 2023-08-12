package com.blisgo.web.controller;

import com.blisgo.constant.Page;
import com.blisgo.exception.GeneralException;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.service.ReplyService;
import com.blisgo.util.RouteUrlHelper;
import com.blisgo.web.dto.ReplyDTO;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("reply")
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    /**
     * 댓글 작성
     *
     * @param principal 인증된 사용자
     * @param bdNo      게시글 번호
     * @param replyDTO  댓글
     * @return mv
     */
    @PostMapping("{bdNo}")
    public ModelAndView replyPOST(
            @AuthenticationPrincipal PrincipalDetails principal,
            @PathVariable int bdNo,
            @Valid ReplyDTO replyDTO
    ) {
        if (!replyService.addReply(replyDTO, bdNo, principal.getAccount().getMemNo())) {
            throw new GeneralException("댓글이 작성되지 않았습니다");
        }

        return new ModelAndView(
                new RedirectView(RouteUrlHelper.combine(Page.board, bdNo), false)
        );
    }

    /**
     * 댓글 삭제
     *
     * @param bdNo    게시글 번호
     * @param replyNo 댓글 번호
     * @return mv
     */
    @DeleteMapping("{bdNo}/{replyNo}")
    public ModelAndView replyRemove(
            @PathVariable int bdNo,
            @PathVariable int replyNo
    ) {
        if (!replyService.removeReply(replyNo, bdNo)) {
            throw new GeneralException("댓글이 삭제되지 않았습니다");
        }

        return new ModelAndView(
                new RedirectView(RouteUrlHelper.combine(Page.board, bdNo), false)
        );
    }
}
