package com.blisgo.web.controller;

import com.blisgo.constant.Folder;
import com.blisgo.constant.Page;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.service.AccountService;
import com.blisgo.util.RouteUrlHelper;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DogamDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("account")
public class AccountController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 회원 로그인
     *
     * @return mv
     */
    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView(
                RouteUrlHelper.combine(Folder.account, Page.login)
        );
    }

    /**
     * 회원가입
     *
     * @return mv
     */
    @GetMapping("register")
    public ModelAndView register() {
        // FIXME 가입시 약관도 파라미터로 전달됨
        return new ModelAndView(
                RouteUrlHelper.combine(Folder.account, Page.register),
                Map.of("termsOfAgreement", accountService.findTermsOfAgreement())
        );
    }

    /**
     * 회원가입 전송
     *
     * @param accountDTO 사용자
     * @return mv
     */
    @PostMapping("register")
    public ModelAndView registerPOST(@Valid AccountDTO accountDTO) {
        if (accountService.addAccount(accountDTO)) {
            log.info("가입 성공, 도감과 커뮤니티 서비스를 이용할 수 있습니다.");
            return new ModelAndView(
                    new RedirectView(RouteUrlHelper.combine(Folder.account, Page.login), false)
            );
        } else {
            log.error("가입 실패, 일반 가입은 중복된 메일로 계정을 등록할 수 없습니다.");
            return new ModelAndView(
                    RouteUrlHelper.combine(Folder.account, Page.register)
            );
        }
    }

    /**
     * 회원 가입시 이메일 중복 확인
     *
     * @param email 사용자
     * @return boolean
     */
    @PostMapping("check")
    public @ResponseBody boolean registerCheck(String email) {
        return accountService.findAccount(email).isEmpty();
    }

    /**
     * 회원 비밀번호 분실 인증
     *
     * @return mv
     */
    @GetMapping("verify")
    public ModelAndView verify() {
        return new ModelAndView(
                RouteUrlHelper.combine(Folder.account, Page.verify)
        );
    }

    @GetMapping("verify/{token}")
    public ModelAndView authentication(
            HttpSession session,
            @PathVariable String token
    ) {
        AtomicReference<String> url = new AtomicReference<>();
        accountService.findAccount(new String(Base64.getDecoder().decode(token))).ifPresentOrElse(
                accountDTO -> {
                    log.info("인증 성공, 이제 비밀번호를 변경할 수 있습니다");
                    session.setAttribute("email", accountDTO.email());
                    url.set(RouteUrlHelper.combine(Folder.account, Page.chgpw));
                },
                () -> {
                    log.error("인증 실패, 잘못된 토큰입니다.");
                    url.set(RouteUrlHelper.combine(Folder.account, Page.chgpw));
                }
        );
        return new ModelAndView(
                new RedirectView(url.get(), false)
        );
    }

    @GetMapping("chgpw")
    public ModelAndView pwchange(HttpSession session) {
        String email = (String) session.getAttribute("email");

        if (email == null) {
            log.error("시스템 오류, 잘못된 접근입니다.");
            return new ModelAndView(
                    new RedirectView(RouteUrlHelper.combine(), false)
            );
        }

        if (accountService.findAccount(email).isEmpty()) {
            session.invalidate();
        }

        return new ModelAndView(
                RouteUrlHelper.combine(Folder.account, Page.chgpw)
        );
    }

    /**
     * 마이페이지
     *
     * @return mv
     */
    @GetMapping("mypage")
    public ModelAndView mypage(@AuthenticationPrincipal PrincipalDetails principal) {
        List<DogamDTO> dogamList = accountService.findDogam(
                principal.getAccount().getMemNo()
        );

        return new ModelAndView(
                RouteUrlHelper.combine(Folder.account, Page.mypage),
                Map.of("dogamList", dogamList)
        );
    }

    @PatchMapping("chgpw")
    public ModelAndView pwchangeConfirm(
            HttpSession session,
            AccountDTO accountDTO
    ) {
        if (accountService.modifyAccountPass(accountDTO, accountDTO.pass())) {
            session.invalidate();
            return new ModelAndView(
                    new RedirectView(RouteUrlHelper.combine(), false)
            );
        } else {
            return new ModelAndView(
                    new RedirectView(RouteUrlHelper.combine(Folder.account, Page.chgpw), false)
            );
        }
    }

    /**
     * 마이페이지 프로필 업데이트
     *
     * @param profile_img 업로드된 프로필 이미지
     * @param principal   인증된 사용자
     * @return mv
     */
    @PatchMapping("mypage/update-profile-img")
    public ModelAndView mypageUpdateProfileImg(
            @RequestParam("upload-img") MultipartFile profile_img,
            @AuthenticationPrincipal PrincipalDetails principal
    ) {
        accountService.modifyAccountProfileImg(
                principal.getAccount().getEmail(),
                profile_img
        ).ifPresentOrElse(
                uploadedProfileImgUrl -> {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication.getPrincipal() instanceof PrincipalDetails user) {
                        user.setProfileImage(uploadedProfileImgUrl);
                    }
                },
                () -> log.warn("프로필 변경이 적용되지 않았습니다")
        );

        return new ModelAndView(
                new RedirectView(RouteUrlHelper.combine(Folder.account, Page.mypage), false)
        );
    }

    /**
     * 회원 비밀번호 변경
     *
     * @param principal 인증된 사용자
     * @param passNew   신규 비밀번호
     * @return mv
     */
    @PatchMapping("mypage/update-password")
    public ModelAndView mypageUpdatePassword(
            @AuthenticationPrincipal PrincipalDetails principal,
            @RequestParam("pass-new") String passNew
    ) {
        accountService.findAccount(principal.getAccount().getEmail()).ifPresentOrElse(
                accountDTO -> {
                    accountService.modifyAccountPass(accountDTO, passNew);
                    log.info("해당 계정의 비밀번호가 변경되었습니다");
                },
                () -> log.error("시스템 오류, 잘못된 접근입니다.")
        );

        return new ModelAndView(
                new RedirectView(RouteUrlHelper.combine(Folder.account, Page.login), false)
        );
    }

    /**
     * 마이페이지 계정 삭제
     *
     * @return mv
     */
    @DeleteMapping("mypage")
    public ModelAndView mypageDeleteAccount(
            @AuthenticationPrincipal PrincipalDetails principal,
            HttpSession session) {
        if (accountService.removeAccount(principal.getAccount().getMemNo())) {
            session.invalidate();
        }

        return new ModelAndView(
                new RedirectView(RouteUrlHelper.combine(), false)
        );
    }

    /**
     * 마이페이지 도감 더보기
     *
     * @param principal 인증된 사용자
     * @return json
     */
    @PostMapping("dogam/more")
    public @ResponseBody List<DogamDTO> dictionaryLoadMore(@AuthenticationPrincipal PrincipalDetails principal) {
        return accountService.findDogamMore(
                principal.getAccount().getMemNo()
        );
    }

    /**
     * 회원 로그아웃
     *
     * @return mv
     */
    @GetMapping("logout")
    public ModelAndView logout() {
        return new ModelAndView(
                new RedirectView(RouteUrlHelper.combine())
        );
    }
}