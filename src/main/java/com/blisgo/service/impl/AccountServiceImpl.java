package com.blisgo.service.impl;

import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.domain.mapper.DogamMapper;
import com.blisgo.domain.repository.AccountRepository;
import com.blisgo.service.AccountService;
import com.blisgo.util.CloudinaryUtil;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DogamDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    CloudinaryUtil cloudinaryUtil;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private static int index = 0;
    private static final int limit = 24;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean addAccount(AccountDTO accountDTO) {
        accountDTO = AccountDTO.addAccount(accountDTO);
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        return accountRepository.insertAccount(account);
    }

    @Override
    public Optional<AccountDTO> findAccount(AccountDTO accountDTO) {
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        var rs = accountRepository.selectAccount(account);
        return Optional.ofNullable(AccountMapper.INSTANCE.toDTO(rs));
    }

    @Override
    public boolean removeAccount(AccountDTO accountDTO) {
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        return accountRepository.deleteAccount(account);
    }

    @Override
    public boolean modifyAccountPass(AccountDTO accountDTO, String passNew) {
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        return accountRepository.updatePassword(account, bCryptPasswordEncoder.encode(passNew));
    }

    @Override
    public List<DogamDTO> findDogam(AccountDTO accountDTO) {
        index = 0;
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        var rs = accountRepository.selectDogamList(account, index, limit);
        return DogamMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public List<DogamDTO> findDogamMore(AccountDTO accountDTO) {
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        index += limit;
        var rs = accountRepository.selectDogamList(account, index, limit);
        return DogamMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public boolean modifyAccountProfileImg(AccountDTO accountDTO, MultipartFile profile_img) {
        cloudinaryUtil = new CloudinaryUtil();
        String profile_img_url = cloudinaryUtil.uploadFile(profile_img, "account");
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        return accountRepository.updateProfileImg(account, profile_img_url);
    }

    @Override
    public String findTermsOfAgreement() {
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream in = classLoader.getResourceAsStream("static/agreement.txt")) {
            InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(in));
            Stream<String> streamOfString = new BufferedReader(inputStreamReader).lines();
            return streamOfString.collect(Collectors.joining());
        } catch (IOException e) {
            e.fillInStackTrace();
            return null;
        }
    }
}
