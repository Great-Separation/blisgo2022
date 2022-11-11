package com.blisgo.domain.repository.impl;

import com.blisgo.config.TestQueryDslConfig;
import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Dogam;
import com.blisgo.domain.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Slf4j
@DataJpaTest
@Import(TestQueryDslConfig.class)
class AccountRepositoryImplTest extends TestRepositoryTemplate {

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        initData();
        entityAssistant(entityAssistOpt.PERSIST, sampleDictionary, sampleAccount, sampleDogam);
    }

    @AfterEach
    void tearDown() {
        printJaversMonitorResult();
        entityManager.clear();
        entityAssistant(entityAssistOpt.AUTOINCREMEMT, sampleDictionary, sampleAccount);
    }

    @Nested
    @DisplayName("logon.html")
    class loginPage {
        @Test
        @DisplayName("계정이 조회되었는가")
        void testSelectAccount() {
            Account result = accountRepository.selectAccount(sampleAccount);
            diff = javers.compare(sampleAccount, result);
            Assertions.assertFalse(diff.hasChanges());
        }
    }

    @Nested
    @DisplayName("register.html")
    class registerPage {
        @Test
        @DisplayName("계정이 삽입되었는가?")
        void testInsertAccount() {
            boolean result = accountRepository.insertAccount(sampleAccount);
            Assertions.assertTrue(result);
        }
    }

    @Nested
    @DisplayName("mypage.html")
    class mypagePage {

        @Test
        @DisplayName("계정이 삭제되었는가?")
        void testDeleteAccount() {
            boolean result = accountRepository.deleteAccount(sampleAccount);
            Assertions.assertTrue(result);
        }

        @Test
        @DisplayName("계정 비밀번호가 수정되었는가?")
        void testUpdatePassword() {
            boolean result = accountRepository.updatePassword(sampleAccount, "newPass");
            Assertions.assertTrue(result);
        }

        @Test
        @DisplayName("도감 다건 조회되었는가?")
        void testSelectDogamList() {
            List<Dogam> result = accountRepository.selectDogamList(sampleAccount, 0, 24);
            diff = javers.compare(sampleDogam, result.get(0));
            Assertions.assertNotNull(result);
        }

        @Test
        @DisplayName("계정 프로필 이지미가 수정되었는가?")
        void testUpdateProfileImg() {
            boolean result = accountRepository.updateProfileImg(sampleAccount, "profile_img_url");
            Assertions.assertTrue(result);
        }
    }


}