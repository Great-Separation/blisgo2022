package com.blisgo.security.auth;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.selectAccount(Account.builder().email(email).build());
        if (account == null) {
            return (UserDetails) new UsernameNotFoundException("사용자 정보가 없습니다.");
        } else {
            return new PrincipalDetails(account);
        }

    }

}
