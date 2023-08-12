package com.blisgo.service.impl;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.domain.mapper.DogamMapper;
import com.blisgo.domain.repository.AccountRepository;
import com.blisgo.service.AccountService;
import com.blisgo.util.CloudinaryUtil;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DogamDTO;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private static int index = 0;
    private static final int limit = 48;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean addAccount(AccountDTO accountDTO) {
        return accountRepository.insertAccount(
                Account.addAccount(accountDTO)
        );
    }

    @Override
    public Optional<AccountDTO> findAccount(String email) {
        return accountRepository.selectAccount(email)
                .map(AccountMapper.INSTANCE::toDTO)
                .map(Optional::of)
                .orElseGet(() -> {
                    log.warn("해당 계정이 존재하지 않습니다: " + email);
                    return Optional.empty();
                });
    }

    @Override
    public boolean removeAccount(int memNo) {
        if (accountRepository.deleteAccount(memNo) > 0) {
            log.info("계정 탈퇴 완료");
            return true;
        } else {
            log.error("계정 탈퇴 실패");
            return false;
        }
    }

    @Override
    public boolean modifyAccountPass(AccountDTO accountDTO, String passNew) {
        if (accountRepository.updatePassword(
                AccountMapper.INSTANCE.toEntity(accountDTO),
                bCryptPasswordEncoder.encode(passNew)) == 1
        ) {
            log.info("변경 완료, 변경된 비밀번호로 다시 로그인 해주세요.");
            return true;
        } else {
            log.warn("변경 실패, 이전 비밀번호가 틀렸습니다. 다시 확인바랍니다.");
            return false;
        }
    }

    @Override
    public List<DogamDTO> findDogam(int memNo) {
        index = 0;
        return DogamMapper.INSTANCE.toDTOList(
                accountRepository.selectDogamList(
                        memNo, index, limit
                )
        );
    }

    @Override
    public List<DogamDTO> findDogamMore(int memNo) {
        index += limit;
        return DogamMapper.INSTANCE.toDTOList(
                accountRepository.selectDogamList(
                        memNo, index, limit
                )
        );
    }

    @Override
    public Optional<String> modifyAccountProfileImg(String email, MultipartFile profile_img) {
        String uploadedUrl = new CloudinaryUtil().uploadFile(profile_img, "account");
        return switch ((int) accountRepository.updateProfileImg(email, uploadedUrl)) {
            case 0 -> {
                log.warn("지정한 프로필이 변경되지 않았습니다");
                yield Optional.empty();
            }
            case 1 -> {
                log.info("지정한 프로필이 성공적으로 변경되었습니다");
                yield Optional.of(uploadedUrl);
            }
            default -> {
                log.error("본인 프로필 외 다수의 사용자 계정 프로필이 변경되었습니다");
                yield Optional.empty();
            }
        };
    }

    @Override
    public String findTermsOfAgreement() {
        final String path = "static/agreement.txt";

        InputStream in = Objects.requireNonNull(getClass()
                .getClassLoader()
                .getResourceAsStream(path)
        );

        try (Stream<String> streamOfString = new BufferedReader(
                new InputStreamReader(in)
        ).lines()) {
            return streamOfString.collect(Collectors.joining());
        }
    }
}