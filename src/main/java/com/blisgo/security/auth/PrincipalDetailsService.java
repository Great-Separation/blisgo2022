package com.blisgo.security.auth;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.mapper.AccountMapper;
import com.blisgo.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.selectAccount(Account.builder().email(email).build());
        if (account == null) {
            return null;
        } else {
            httpSession.setAttribute("mem", AccountMapper.INSTANCE.toDTO(account));
            return new PrincipalDetails(account);
        }

    }

}
