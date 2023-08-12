package com.blisgo.security.oauth;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.repository.AccountRepository;
import com.blisgo.security.auth.PrincipalDetails;
import com.blisgo.security.oauth.provider.GithubUserInfo;
import com.blisgo.security.oauth.provider.GoogleUserInfo;
import com.blisgo.security.oauth.provider.OAuth2UserInfo;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PrincipalOauth2UserService.class);
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public PrincipalOauth2UserService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("userRequest clientRegistration : " + userRequest.getClientRegistration());
        log.info("oAuth2User : " + oAuth2User);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) throws OAuth2AuthenticationException {

        OAuth2UserInfo oAuth2UserInfo = switch (userRequest.getClientRegistration().getRegistrationId()) {
            case "google" -> new GoogleUserInfo(oAuth2User.getAttributes());
            case "github" -> new GithubUserInfo(oAuth2User.getAttributes());
            default -> throw new OAuth2AuthenticationException("미지원 제공자");
        };

        Account account = accountRepository.selectAccount(Objects.requireNonNull(oAuth2UserInfo).getEmail())
                .map(user -> {
                    accountRepository.updateProfileImg(user.getEmail(), oAuth2UserInfo.getProfileImage());
                    return user;
                })
                .orElseGet(() -> {
                    Account newAccount = Account.builder()
                            .nickname(oAuth2UserInfo.getNickname())
                            .email(oAuth2UserInfo.getEmail())
                            .pass(bCryptPasswordEncoder.encode(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId()))
                            .profileImage(oAuth2UserInfo.getProfileImage())
                            .provider(oAuth2UserInfo.getProvider())
                            .providerId(oAuth2UserInfo.getProviderId())
                            .build();
                    accountRepository.insertAccount(newAccount);
                    return newAccount;
                });

        return new PrincipalDetails(account, oAuth2User.getAttributes());
    }
}