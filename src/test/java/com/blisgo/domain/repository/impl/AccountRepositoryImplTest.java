package com.blisgo.domain.repository.impl;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.repository.AccountRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static org.mockito.Mockito.*;

class AccountRepositoryImplTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void testInsertAccount() {
        accountRepository.insertAccount(new Account(Integer.valueOf(0), "nickname", "email", "pass", Integer.valueOf(0), "profileImage"));
    }

    @Test
    void testSelectAccount() {
        Account result = accountRepository.selectAccount(new Account(null, null, "email", null, null, null));
        Assertions.assertEquals(new Account(null, null, "email", null, null, null), result);
    }

    @Test
    void testUpdateNickname() {
        long result = accountRepository.updateNickname(new Account(null, "nickname", "email", null, null, null));
        Assertions.assertEquals(0L, result);
    }

    @Test
    void testDeleteAccount() {
        accountRepository.deleteAccount(new Account(Integer.valueOf(0), null, null, null, null, null));
    }

    @Test
    void testUpdatePassword() {
        long result = accountRepository.updatePassword(new Account(null, null, "email", null, null, null), "newPass");
        Assertions.assertEquals(0L, result);
    }

    @Test
    void testSelectDogamList() {
        List<Dogam> result = accountRepository.selectDogamList(new Account(Integer.valueOf(0), "nickname", "email", "pass", Integer.valueOf(0), "profileImage"), 0, 0);
        Assertions.assertEquals(List.of(new Dogam(new Account(Integer.valueOf(0), "nickname", "email", "pass", Integer.valueOf(0), "profileImage"), new Dictionary(Integer.valueOf(0), "name", "engName", "category", Integer.valueOf(0), Short.valueOf((short) 0), "thumbnail", "treatment"))), result);
    }

    @Test
    void testUpdateProfileImg() {
        accountRepository.updateProfileImg(new Account(null, null, "email", null, null, null), "profile_img_url");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme