package com.blisgo.security.oauth;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.domain.repository.AccountRepository;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.security.oauth.provider.GithubUserInfo;
import com.blisgo.security.oauth.provider.GoogleUserInfo;
import com.blisgo.security.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final AccountRepository accountRepository;
    private final HttpSession httpSession;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("userRequest clientRegistration : " + userRequest.getClientRegistration());
        log.info("oAuth2User : " + oAuth2User);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        OAuth2UserInfo oAuth2UserInfo = null;
        switch (userRequest.getClientRegistration().getRegistrationId()) {
            case "google" -> oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
            case "github" -> oAuth2UserInfo = new GithubUserInfo(oAuth2User.getAttributes());
            default -> log.error("미지원 제공자");
        }

        Optional<Account> userOptional = Optional.ofNullable(accountRepository.selectAccount(Account.builder().email(Objects.requireNonNull(oAuth2UserInfo).getEmail()).build()));
        Account account;
        if (userOptional.isPresent()) {
            account = userOptional.get();

            accountRepository.updateProfileImg(account, oAuth2UserInfo.getProfileImage());
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            account = Account.builder()
                    .nickname(oAuth2UserInfo.getNickname())
                    .email(oAuth2UserInfo.getEmail())
                    .pass(bCryptPasswordEncoder.encode(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId()))
                    .profileImage(oAuth2UserInfo.getProfileImage())
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            accountRepository.insertAccount(account);
        }
        httpSession.setAttribute("mem", AccountMapper.INSTANCE.toDTO(account));
        return new PrincipalDetails(account, oAuth2User.getAttributes());
    }
}
