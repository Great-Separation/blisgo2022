package com.blisgo.service.impl;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.domain.mapper.DogamMapper;
import com.blisgo.domain.repository.AccountRepository;
import com.blisgo.service.AccountService;
import com.blisgo.web.dto.AccountDTO;
import com.blisgo.web.dto.DogamDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private static int index = 0;
    private static final int limit = 24;

    @Override
    public boolean addAccount(AccountDTO accountDTO) {
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        String default_profile_img = "https://ui-avatars.com/api/?background=random&name=" + account.getEmail();
        account = Account.createAccount(account, default_profile_img);
        accountRepository.insertAccount(account);
        accountRepository.updateProfileImg(account, default_profile_img);
        return true;
    }

    @Override
    public AccountDTO findAccount(AccountDTO accountDTO) {
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        var rs = accountRepository.selectAccount(account);
        return AccountMapper.INSTANCE.toDTO(rs);

    }

    @Override
    public boolean removeAccount(AccountDTO accountDTO) {
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        accountRepository.deleteAccount(account);
        return true;
    }

    @Override
    public boolean modifyAccountPass(AccountDTO accountDTO, String newPass) {
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        return accountRepository.updatePassword(account, newPass);
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

        // 더이상 조회되는 내용이 없을때의 오류 방지
        var rs = accountRepository.selectDogamList(account, index, limit);
        return DogamMapper.INSTANCE.toDTOList(rs);
    }

    @Override
    public boolean modifyAccountProfileImg(AccountDTO accountDTO, String profile_img_url) {
        var account = AccountMapper.INSTANCE.toEntity(accountDTO);
        return accountRepository.updateProfileImg(account, profile_img_url);
    }

    @Override
    public String findTermsOfAgreement() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream("static/agreement.txt");

        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(in));
        Stream<String> streamOfString = new BufferedReader(inputStreamReader).lines();
        return streamOfString.collect(Collectors.joining());
    }

}
