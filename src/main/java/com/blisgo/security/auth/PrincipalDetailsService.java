package com.blisgo.security.auth;

import com.blisgo.domain.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public PrincipalDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new PrincipalDetails(
                accountRepository.selectAccount(email).orElseThrow(() ->
                        new UsernameNotFoundException("사용자 정보가 없습니다.")
                )
        );
    }

}