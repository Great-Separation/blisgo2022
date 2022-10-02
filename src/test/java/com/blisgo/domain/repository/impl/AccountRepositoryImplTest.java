package com.blisgo.domain.repository.impl;

import com.blisgo.config.TestQueryDslConfig;
import com.blisgo.domain.entity.Account;
import com.blisgo.domain.entity.Dictionary;
import com.blisgo.domain.entity.Dogam;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.ListCompareAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@DataJpaTest
@Import(TestQueryDslConfig.class)
class AccountRepositoryImplTest {
    enum entityAssistOpt {
        PERSIST, AUTOINCREMEMT
    }

    @Autowired
    EntityManager entityManager;
    @Autowired
    AccountRepositoryImpl accountRepository;
    Javers javers;
    Diff diff;

    Dictionary sampleDictionary;
    Account sampleAccount;
    Dogam sampleDogam;

    private void entityAssistant(@NotNull entityAssistOpt opt, Object... entities) {
        switch (opt) {
            case PERSIST:
                for (var e : entities)
                    entityManager.persist(e);
                break;

            case AUTOINCREMEMT:
                for (var e : entities)
                    entityManager.createNativeQuery(String.format("ALTER TABLE %s AUTO_INCREMENT = 1", e.getClass().getSimpleName())).executeUpdate();
                break;

            default:
                break;
        }
    }

    private void initData() {
        sampleDictionary = Dictionary.builder().name("name").engName("engName").category("category").popularity(0).hit((short) 0).thumbnail("thumbnail").treatment("treatment").build();
        sampleAccount = Account.builder().nickname("nickname").email("email").pass("pass").memPoint(0).profileImage("profileImage").build();
        sampleDogam = Dogam.builder().account(sampleAccount).dictionary(sampleDictionary).build();
    }

    @BeforeEach
    void monitorEntity() {
        initData();
        entityAssistant(entityAssistOpt.PERSIST, sampleDictionary, sampleAccount, sampleDogam);
        javers = JaversBuilder.javers().withListCompareAlgorithm(ListCompareAlgorithm.LEVENSHTEIN_DISTANCE).build();
    }

    @AfterEach
    void manageEntity() {
        if (diff != null) {
            System.out.println("감사 결과>" + diff.changesSummary());
            diff.getChanges().forEach(change -> System.out.println("- " + change));
        } else {
            log.info("해당 테스트에서는 엔티티 감사 안했음");
        }
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
            Assertions.assertNotNull(result);
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
        @DisplayName("계정 닉네임이 수정되었는가?")
        void testUpdateNickname() {
            boolean result = accountRepository.updateNickname(sampleAccount);
            Assertions.assertTrue(result);
        }

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