package com.blisgo.security.auth;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public PrincipalDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> account = Optional.ofNullable(accountRepository.selectAccount(Account.builder().email(email).build()));
        if (account.isPresent()) {
            return new PrincipalDetails(account.get());
        } else {
            throw new UsernameNotFoundException("사용자 정보가 없습니다.");
        }
    }

}
