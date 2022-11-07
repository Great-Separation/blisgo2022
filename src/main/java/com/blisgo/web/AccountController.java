package com.blisgo.web;

import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.service.AccountService;
import com.blisgo.util.CloudinaryUtil;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DogamDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("account")
public class AccountController {
    private final AccountService accountService;

    private final ModelAndView mv = new ModelAndView();
    CloudinaryUtil cloudinaryUtil;
    String url;

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
    @PostMapping("login")
    public ModelAndView login(AccountDTO accountDTO) {
        AccountDTO registeredAccount = accountService.findAccount(accountDTO);

        if (registeredAccount == null) {
            // alertMsg = new AlertMsg(res, "없는 회원입니다. 회원가입 후 로그인 진행하시길 바랍니다.",
            // "insertBoarder");
            url = RouteUrlHelper.combine(folder.account, page.register);
        } else {
            if (accountDTO.getPass().equals(registeredAccount.getPass())) {
                // alertMsg = new AlertMsg(res, "/");
                url = RouteUrlHelper.combine("");
            } else {
                // alertMsg = new AlertMsg(res, "비밀번호가 틀렸습니다. 다시 확인해주세요", "login");
                url = RouteUrlHelper.combine(folder.account, page.login);
            }
        }
        mv.setView(new RedirectView(url, false));
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
    public ModelAndView registerPOST(@Valid AccountDTO accountDTO) {
        if (accountService.addAccount(accountDTO)) {
            // alertMsg = new AlertMsg(res, "회원가입 성공", "login");
            url = RouteUrlHelper.combine(folder.account, page.login);
            mv.setView(new RedirectView(url, false));
        } else {
            // alertMsg = new AlertMsg(res, "회원가입 실패", "register");
            url = RouteUrlHelper.combine(folder.account, page.register);
            mv.setViewName(url);
        }
        return mv;
    }

    // FIXME [이메일 중복 확인] 프론트단 ajax 수정 필요
//	@PostMapping("register/check")
//	public @ResponseBody boolean registerCheck(AccountDTO userDTO) {
//		if (accountService.emailCheck(userDTO) > 0) {
//			return false;
//		} else {
//			return true;
//		}
//	}

    /**
     * 회원 비밀번호 분실 인증
     *
     * @return mv
     */
    @GetMapping("verify")
    public ModelAndView verify() {
        url = RouteUrlHelper.combine(folder.account, page.verify);
        return mv;
    }

    // FIXME [회원 비밀번호 분실 인증 이메일, 전화번호 전송] 테스트 불가. 메일전송 API 교체 혹은 네이버 SMS API 사용
//	@PostMapping("verify")
//	public ModelAndView verifyEmailPOST(ModelAndView mv, HttpSession session, AccountDTO userDTO) {
//
//		if (accountService.emailCheck(userDTO) > 0) {
//			String email = userDTO.email();
//			String nickname = userDTO.nickname();
//
//			try {
//				sendGridMail.sendTemplateEmail(email, nickname);
//			} catch (IOException e) {
//				// 메일 전송 실패
//				e.printStackTrace();
//			}
//			session.setAttribute("verifyEmail", email);
//			// alertMsg = new AlertMsg(res, "메일이 전송되었습니다", "/");
//			mv.setViewName("/");
//		} else {
//			// alertMsg = new AlertMsg(res, "없는 계정입니다", "verify");
//			mv.setViewName("verify");
//		}
//		return mv;
//	}

    // FIXME [이메일 혹은 전화번호 인증 후 비밀번호 변경 페이지] 테스트 불가. 메일전송 API 교체 혹은 네이버 SMS API 사용
//	@GetMapping("chgpw")
//	public ModelAndView pwchange(ModelAndView mv, HttpSession session,
//			@RequestParam("confirmEmail") String confirmEmail) {
//		String verifyEmail = (String) session.getAttribute("verifyEmail");
//
//		if (verifyEmail.equals(confirmEmail)) {
//			url = RouteUrlHelper.combine(folder.user, page.chgpw);
//		} else {
//			session.invalidate();
//		}
//		mv.setViewName(url);
//		return mv;
//	}

    // TODO [이메일 혹은 전화번호 인증 후 비밀번호 변경 페이지(POST)] 사용 위치 파악 후 조치
//	@PutMapping("changepwd")
//	public ModelAndView pwchangeConfirm(ModelAndView mv, HttpSession session, HttpServletRequest req,
//			HttpServletResponse res, AccountDTO userDTO) {
//		Account user = modelMapper.map(userDTO, Account.class);
//
//		String pass = req.getParameter("pw-new");
//		String newPwConfirm = req.getParameter("pw-confirm");
//
//		if (pass.equals(newPwConfirm)) {
//			accountService.updatePassword(user, pass);
//			session.invalidate();
//			// alertMsg = new AlertMsg(res, "비밀번호가 변경되었습니다", "/");
//			mv.setViewName("/");
//		} else {
//			// alertMsg = new AlertMsg(res, "입력한 비밀번호가 일치하지 않습니다", "/changepwd");
//			mv.setViewName("changepwd");
//		}
//
//		return mv;
//	}

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
            @RequestParam("upload-img") MultipartFile profile_img, @AuthenticationPrincipal PrincipalDetails principal) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());
        cloudinaryUtil = new CloudinaryUtil();
        String profile_img_url = cloudinaryUtil.uploadFile(profile_img, folder.account.toString());

        accountService.modifyAccountProfileImg(accountDTO, profile_img_url);
        url = RouteUrlHelper.combine(folder.account, page.mypage);
        mv.setView(new RedirectView(url, false));
        return mv;
    }

    /**
     * 마이페이지 닉네임 변경
     *
     * @param principal 인증된 사용자
     * @return mv
     */
    @PutMapping("mypage/update-nickname")
    public ModelAndView mypageUpdateNickname(@AuthenticationPrincipal PrincipalDetails principal) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());

        if (accountService.modifyAccountNickname(accountDTO)) {
            accountDTO = accountService.findAccount(accountDTO);
            // alertMsg = new AlertMsg(res, "회원 정보가 변경되었습니다.", "mypage");
            url = RouteUrlHelper.combine(folder.account, page.mypage);
            mv.setView(new RedirectView(url, false));
        } else {
            // alertMsg = new AlertMsg(res, "회원 정보 변경이 실패했습니다.", "/changepwd");
            url = RouteUrlHelper.combine(folder.account, page.chgpw);
            mv.setViewName(url);
        }

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
                                             @RequestParam("newPass") String newPass) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());

        if (accountDTO.getPass().equals(accountService.findAccount(accountDTO).getPass())) {
            accountService.modifyAccountPass(accountDTO, newPass);
            // alertMsg = new AlertMsg(res, "변경된 비밀번호로 다시 로그인바랍니다.", "login");
            url = RouteUrlHelper.combine(folder.account, page.login);
            mv.setView(new RedirectView(url, false));
        } else {
            // alertMsg = new AlertMsg(res, "이전 비밀번호가 틀렸습니다. 다시 확인바랍니다.", "mypage");
            url = RouteUrlHelper.combine(folder.account, page.mypage);
            mv.setViewName(url);
        }
        return mv;
    }

    /**
     * 마이페이지 계정 삭제
     *
     * @return mv
     */
    @DeleteMapping("mypage")
    public ModelAndView mypageDeleteAccount(@AuthenticationPrincipal PrincipalDetails principal) {
        AccountDTO accountDTO = AccountMapper.INSTANCE.toDTO(principal.getAccount());
        if (accountService.removeAccount(accountDTO)) {
            // alertMsg = new AlertMsg(res, "회원 탈퇴되었습니다.", "/logout");
            url = RouteUrlHelper.combine("");
            mv.setView(new RedirectView(url, false));
        } else {
            // alertMsg = new AlertMsg(res, "회원 탈퇴 실패했습니다. 다시 시도해주시기바랍니다.", "mypage");
            url = RouteUrlHelper.combine(folder.account, page.mypage);
            mv.setViewName(url);
        }
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
        url = RouteUrlHelper.combine("");
        mv.setView(new RedirectView(url));
        return mv;
    }
}
