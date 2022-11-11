package com.blisgo.web;

import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.service.AccountService;
import com.blisgo.util.AlertMsg;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DogamDTO;
import com.sun.xml.messaging.saaj.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;

    private final ModelAndView mv = new ModelAndView();
    private static String url;

    public static AlertMsg alertMsg;

    /**
     * 회원 로그인
     *
     * @return mv
     */
    @GetMapping("login")
    public ModelAndView login() {
        url = RouteUrlHelper.combine(folder.account, page.login);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 회원 로그인 전송
     *
     * @param accountDTO 사용자
     * @return mv
     */
    @Deprecated
    @PostMapping("login")
    public ModelAndView login(AccountDTO accountDTO, RedirectAttributes rtt) {
        Optional<AccountDTO> registeredAccount = accountService.findAccount(accountDTO);

        if (registeredAccount.isPresent()) {
            if (accountDTO.getPass().equals(registeredAccount.get().getPass())) {
                url = RouteUrlHelper.combine();
            } else {
                alertMsg = new AlertMsg(AlertMsg.Icons.warning.name(), "로그인 실패", "비밀번호가 틀렸습니다. 다시 확인해주세요.");
                url = RouteUrlHelper.combine(folder.account, page.login);
            }
        } else {
            alertMsg = new AlertMsg(AlertMsg.Icons.warning.name(), "로그인 실패", "존재하지 않는 계정입니다. 회원가입 후 로그인 해주세요.");
            url = RouteUrlHelper.combine(folder.account, page.register);
        }
        mv.setView(new RedirectView(url, false));
        rtt.addFlashAttribute(alertMsg);
        return mv;
    }

    /**
     * 회원가입
     *
     * @return mv
     */
    @GetMapping("register")
    public ModelAndView register() {
        String termsOfAgreement = accountService.findTermsOfAgreement();
        mv.addObject("termsOfAgreement", termsOfAgreement);

        url = RouteUrlHelper.combine(folder.account, page.register);
        mv.setViewName(url);
        return mv;

    }

    /**
     * 회원가입 전송
     *
     * @param accountDTO 사용자
     * @return mv
     */
    @PostMapping("register")
    public ModelAndView registerPOST(@Valid AccountDTO accountDTO, RedirectAttributes rtt) {
        if (accountService.addAccount(accountDTO)) {
            alertMsg = new AlertMsg(AlertMsg.Icons.success.name(), "가입 성공", "도감과 커뮤니티 서비스를 이용할 수 있습니다.");
            url = RouteUrlHelper.combine(folder.account, page.login);
            mv.setView(new RedirectView(url, false));
        } else {
            alertMsg = new AlertMsg(AlertMsg.Icons.error.name(), "가입 실패", "일반 가입은 중복된 메일로 계정을 등록할 수 없습니다.");
            url = RouteUrlHelper.combine(folder.account, page.register);
            mv.setViewName(url);
        }
        rtt.addFlashAttribute(alertMsg);
        return mv;
    }

    /**
     * 회원 가입시 이메일 중복 확인
     *
     * @param accountDTO 사용자
     * @return boolean
     */
    @PostMapping("check")
    public @ResponseBody boolean registerCheck(AccountDTO accountDTO) {
        return accountService.findAccount(accountDTO).isEmpty();
    }

    /**
     * 회원 비밀번호 분실 인증
     *
     * @return mv
     */
    @GetMapping("verify")
    public ModelAndView verify() {
        url = RouteUrlHelper.combine(folder.account, page.verify);
        mv.setViewName(url);
        return mv;
    }

    @GetMapping("verify/{token}")
    public ModelAndView authentication(HttpSession session, @PathVariable String token, RedirectAttributes rtt) {
        if (accountService.findAccount(AccountDTO.builder().email(Base64.base64Decode(token)).pass(token).build()).isPresent()) {
            alertMsg = new AlertMsg(AlertMsg.Icons.success.name(), "인증 성공", "이제 비밀번호를 변경할 수 있습니다");
            session.setAttribute("email", Base64.base64Decode(token));
            url = RouteUrlHelper.combine(folder.account, page.chgpw);
        } else {
            alertMsg = new AlertMsg(AlertMsg.Icons.error.name(), "인증 실패", "잘못된 토큰입니다.");
            url = RouteUrlHelper.combine();
        }
        mv.setView(new RedirectView(url, false));
        rtt.addFlashAttribute(alertMsg);
        return mv;
    }

    @GetMapping("chgpw")
    public ModelAndView pwchange(HttpSession session, RedirectAttributes rtt) {
        Optional<String> email = Optional.ofNullable((String) session.getAttribute("email"));

        if (email.isPresent()) {
            if (accountService.findAccount(AccountDTO.builder().email(email.get()).pass(email.get()).build()).isPresent()) {
                url = RouteUrlHelper.combine(folder.account, page.chgpw);
                mv.setViewName(url);
            } else {
                session.invalidate();
            }
        } else {
            url = RouteUrlHelper.combine();
            alertMsg = new AlertMsg(AlertMsg.Icons.error.name(), "시스템 오류", "잘못된 접근입니다.");

            mv.setView(new RedirectView(url, false));
        }
        rtt.addFlashAttribute(alertMsg);
        return mv;
    }

    @PutMapping("chgpw")
    public ModelAndView pwchangeConfirm(HttpSession session, AccountDTO accountDTO, RedirectAttributes rtt) {
        accountDTO = AccountDTO.builder().email(accountDTO.getEmail()).pass(accountDTO.getPass()).build();
        if (accountService.modifyAccountPass(accountDTO, accountDTO.getPass())) {
            session.invalidate();
            alertMsg = new AlertMsg(AlertMsg.Icons.success.name(), "변경 완료", "비밀번호가 변경되었습니다.");
            url = RouteUrlHelper.combine();
        } else {
            alertMsg = new AlertMsg(AlertMsg.Icons.error.name(), "변경 실패", "변경하지 못했습니다.");
            url = RouteUrlHelper.combine(folder.account, page.chgpw);
        }
        mv.setView(new RedirectView(url, false));
        rtt.addFlashAttribute(alertMsg);
        return mv;
    }

    /**
     * 마이페이지
     *
     * @return mv
     */
    @GetMapping("mypage")
    public ModelAndView mypage(@AuthenticationPrincipal PrincipalDetails principal) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());
        List<DogamDTO> dogamList = accountService.findDogam(accountDTO);
        mv.addObject("dogamList", dogamList);
        url = RouteUrlHelper.combine(folder.account, page.mypage);
        mv.setViewName(url);
        return mv;
    }

    /**
     * 마이페이지 프로필 업데이트
     *
     * @param profile_img 업로드된 프로필 이미지
     * @param principal   인증된 사용자
     * @return mv
     */
    // FIXME [마이페이지 프로필 업데이트] 회원 정보 변경 즉시 반영되지 않음
    @PutMapping("mypage/update-profile-img")
    public ModelAndView mypageUpdateProfileImg(
            @RequestParam("upload-img") MultipartFile profile_img, @AuthenticationPrincipal PrincipalDetails principal, RedirectAttributes rtt) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());
        if (!accountService.modifyAccountProfileImg(accountDTO, profile_img)) {
            alertMsg = new AlertMsg(AlertMsg.Icons.warning.name(), "변경 실패", "프로필이 변경되지 않았습니다.");
            rtt.addFlashAttribute(alertMsg);
        }
        url = RouteUrlHelper.combine(folder.account, page.mypage);
        mv.setView(new RedirectView(url, false));
        return mv;
    }

    /**
     * 회원 비밀번호 변경
     *
     * @param principal 인증된 사용자
     * @param newPass   신규 비밀번호
     * @return mv
     */
    @PutMapping("mypage/update-password")
    public ModelAndView mypageUpdatePassword(@AuthenticationPrincipal PrincipalDetails principal,
                                             @RequestParam("newPass") String newPass, RedirectAttributes rtt) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());
        var rs = accountService.findAccount(accountDTO);
        if (rs.isPresent()) {
            if (accountDTO.getPass().equals(rs.get().getPass())) {
                accountService.modifyAccountPass(accountDTO, newPass);
                alertMsg = new AlertMsg(AlertMsg.Icons.success.name(), "변경 완료", "변경된 비밀번호로 다시 로그인 해주세요.");
                url = RouteUrlHelper.combine(folder.account, page.login);
            } else {
                alertMsg = new AlertMsg(AlertMsg.Icons.error.name(), "변경 실패", "이전 비밀번호가 틀렸습니다. 다시 확인바랍니다.");
                url = RouteUrlHelper.combine(folder.account, page.mypage);
            }
        } else {
            alertMsg = new AlertMsg(AlertMsg.Icons.error.name(), "시스템 오류", "잘못된 접근입니다.");
            url = RouteUrlHelper.combine(folder.account, page.login);
        }


        mv.setView(new RedirectView(url, false));
        rtt.addFlashAttribute(alertMsg);
        return mv;
    }

    /**
     * 마이페이지 계정 삭제
     *
     * @return mv
     */
    @DeleteMapping("mypage")
    public ModelAndView mypageDeleteAccount(@AuthenticationPrincipal PrincipalDetails principal, RedirectAttributes rtt) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());
        if (accountService.removeAccount(accountDTO)) {
            alertMsg = new AlertMsg(AlertMsg.Icons.success.name(), "탈퇴 완료", "지금까지 이용해주셔서 감사합니다.");
            mv.setView(new RedirectView(url, false));
        } else {
            alertMsg = new AlertMsg(AlertMsg.Icons.error.name(), "탈퇴 실패", "다시 시도해주세요.");
            mv.setView(new RedirectView(url, false));
        }
        mv.setViewName(url);
        rtt.addFlashAttribute(alertMsg);
        return mv;
    }

    /**
     * 마이페이지 도감 더보기
     *
     * @param principal 인증된 사용자
     * @return json
     */
    @PostMapping("dogam/more")
    public @ResponseBody List<DogamDTO> dictionaryLoadMore(@AuthenticationPrincipal PrincipalDetails principal) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());
        return accountService.findDogamMore(accountDTO);
    }

    /**
     * 회원 로그아웃
     *
     * @return mv
     */
    @GetMapping("logout")
    public ModelAndView logout() {
        url = RouteUrlHelper.combine();
        mv.setView(new RedirectView(url));
        return mv;
    }
}
