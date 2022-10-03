package com.blisgo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.repository.AccountRepository;
import com.blisgo.web.dto.AccountDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AccountServiceImplTest {
    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    /**
     * Method under test: {@link AccountServiceImpl#addAccount(AccountDTO)}
     */
    @Test
    void testAddAccount() {
        when(accountRepository.insertAccount((Account) any())).thenReturn(true);
        when(accountRepository.updateProfileImg((Account) any(), (String) any())).thenReturn(true);
        assertTrue(accountServiceImpl.addAccount(new AccountDTO()));
        verify(accountRepository).insertAccount((Account) any());
        verify(accountRepository).updateProfileImg((Account) any(), (String) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#addAccount(AccountDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddAccount2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.domain.entity.Account.getEmail()" because "account" is null
        //       at com.blisgo.service.impl.AccountServiceImpl.addAccount(AccountServiceImpl.java:36)
        //   In order to prevent addAccount(AccountDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   addAccount(AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        when(accountRepository.insertAccount((Account) any())).thenReturn(true);
        when(accountRepository.updateProfileImg((Account) any(), (String) any())).thenReturn(true);
        accountServiceImpl.addAccount(null);
    }

    /**
     * Method under test: {@link AccountServiceImpl#findAccount(AccountDTO)}
     */
    @Test
    void testFindAccount() {
        Account account = new Account();
        account.preUpdate();
        when(accountRepository.selectAccount((Account) any())).thenReturn(account);
        AccountDTO actualFindAccountResult = accountServiceImpl.findAccount(new AccountDTO());
        assertNull(actualFindAccountResult.getCreatedDate());
        assertNull(actualFindAccountResult.getProfileImage());
        assertNull(actualFindAccountResult.getPass());
        assertNull(actualFindAccountResult.getNickname());
        assertNull(actualFindAccountResult.getMemPoint());
        assertNull(actualFindAccountResult.getMemNo());
        assertNull(actualFindAccountResult.getEmail());
        verify(accountRepository).selectAccount((Account) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#findAccount(AccountDTO)}
     */
    @Test
    void testFindAccount2() {
        Account account = mock(Account.class);
        when(account.getMemNo()).thenReturn(1);
        when(account.getMemPoint()).thenReturn(1);
        when(account.getEmail()).thenReturn("jane.doe@example.org");
        when(account.getNickname()).thenReturn("Nickname");
        when(account.getPass()).thenReturn("Pass");
        when(account.getProfileImage()).thenReturn("Profile Image");
        when(account.getCreatedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        doNothing().when(account).preUpdate();
        account.preUpdate();
        when(accountRepository.selectAccount((Account) any())).thenReturn(account);
        AccountDTO actualFindAccountResult = accountServiceImpl.findAccount(new AccountDTO());
        assertEquals("Profile Image", actualFindAccountResult.getProfileImage());
        assertEquals("01:01", actualFindAccountResult.getCreatedDate().toLocalTime().toString());
        assertEquals("Pass", actualFindAccountResult.getPass());
        assertEquals("Nickname", actualFindAccountResult.getNickname());
        assertEquals(1, actualFindAccountResult.getMemPoint().intValue());
        assertEquals(1, actualFindAccountResult.getMemNo().intValue());
        assertEquals("jane.doe@example.org", actualFindAccountResult.getEmail());
        verify(accountRepository).selectAccount((Account) any());
        verify(account).getMemNo();
        verify(account).getMemPoint();
        verify(account).getEmail();
        verify(account).getNickname();
        verify(account).getPass();
        verify(account).getProfileImage();
        verify(account).getCreatedDate();
        verify(account).preUpdate();
    }

    /**
     * Method under test: {@link AccountServiceImpl#findAccount(AccountDTO)}
     */
    @Test
    void testFindAccount3() {
        Account account = mock(Account.class);
        when(account.getMemNo()).thenReturn(1);
        when(account.getMemPoint()).thenReturn(1);
        when(account.getEmail()).thenReturn("jane.doe@example.org");
        when(account.getNickname()).thenReturn("Nickname");
        when(account.getPass()).thenReturn("Pass");
        when(account.getProfileImage()).thenReturn("Profile Image");
        when(account.getCreatedDate()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        doNothing().when(account).preUpdate();
        account.preUpdate();
        when(accountRepository.selectAccount((Account) any())).thenReturn(account);
        AccountDTO actualFindAccountResult = accountServiceImpl.findAccount(null);
        assertEquals("Profile Image", actualFindAccountResult.getProfileImage());
        assertEquals("01:01", actualFindAccountResult.getCreatedDate().toLocalTime().toString());
        assertEquals("Pass", actualFindAccountResult.getPass());
        assertEquals("Nickname", actualFindAccountResult.getNickname());
        assertEquals(1, actualFindAccountResult.getMemPoint().intValue());
        assertEquals(1, actualFindAccountResult.getMemNo().intValue());
        assertEquals("jane.doe@example.org", actualFindAccountResult.getEmail());
        verify(accountRepository).selectAccount((Account) any());
        verify(account).getMemNo();
        verify(account).getMemPoint();
        verify(account).getEmail();
        verify(account).getNickname();
        verify(account).getPass();
        verify(account).getProfileImage();
        verify(account).getCreatedDate();
        verify(account).preUpdate();
    }

    /**
     * Method under test: {@link AccountServiceImpl#modifyAccountNickname(AccountDTO)}
     */
    @Test
    void testModifyAccountNickname() {
        when(accountRepository.updateNickname((Account) any())).thenReturn(true);
        assertTrue(accountServiceImpl.modifyAccountNickname(new AccountDTO()));
        verify(accountRepository).updateNickname((Account) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#modifyAccountNickname(AccountDTO)}
     */
    @Test
    void testModifyAccountNickname2() {
        when(accountRepository.updateNickname((Account) any())).thenReturn(false);
        assertFalse(accountServiceImpl.modifyAccountNickname(new AccountDTO()));
        verify(accountRepository).updateNickname((Account) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#modifyAccountNickname(AccountDTO)}
     */
    @Test
    void testModifyAccountNickname3() {
        when(accountRepository.updateNickname((Account) any())).thenReturn(true);
        assertTrue(accountServiceImpl.modifyAccountNickname(null));
        verify(accountRepository).updateNickname((Account) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#removeAccount(AccountDTO)}
     */
    @Test
    void testRemoveAccount() {
        when(accountRepository.deleteAccount((Account) any())).thenReturn(true);
        assertTrue(accountServiceImpl.removeAccount(new AccountDTO()));
        verify(accountRepository).deleteAccount((Account) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#removeAccount(AccountDTO)}
     */
    @Test
    void testRemoveAccount2() {
        when(accountRepository.deleteAccount((Account) any())).thenReturn(true);
        assertTrue(accountServiceImpl.removeAccount(null));
        verify(accountRepository).deleteAccount((Account) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#modifyAccountPass(AccountDTO, String)}
     */
    @Test
    void testModifyAccountPass() {
        when(accountRepository.updatePassword((Account) any(), (String) any())).thenReturn(true);
        assertTrue(accountServiceImpl.modifyAccountPass(new AccountDTO(), "New Pass"));
        verify(accountRepository).updatePassword((Account) any(), (String) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#modifyAccountPass(AccountDTO, String)}
     */
    @Test
    void testModifyAccountPass2() {
        when(accountRepository.updatePassword((Account) any(), (String) any())).thenReturn(false);
        assertFalse(accountServiceImpl.modifyAccountPass(new AccountDTO(), "New Pass"));
        verify(accountRepository).updatePassword((Account) any(), (String) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#modifyAccountPass(AccountDTO, String)}
     */
    @Test
    void testModifyAccountPass3() {
        when(accountRepository.updatePassword((Account) any(), (String) any())).thenReturn(true);
        assertTrue(accountServiceImpl.modifyAccountPass(null, "New Pass"));
        verify(accountRepository).updatePassword((Account) any(), (String) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#findDogam(AccountDTO)}
     */
    @Test
    void testFindDogam() {
        when(accountRepository.selectDogamList((Account) any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        assertTrue(accountServiceImpl.findDogam(new AccountDTO()).isEmpty());
        verify(accountRepository).selectDogamList((Account) any(), anyInt(), anyInt());
    }

    /**
     * Method under test: {@link AccountServiceImpl#findDogam(AccountDTO)}
     */
    @Test
    void testFindDogam2() {
        when(accountRepository.selectDogamList((Account) any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        assertTrue(accountServiceImpl.findDogam(null).isEmpty());
        verify(accountRepository).selectDogamList((Account) any(), anyInt(), anyInt());
    }

    /**
     * Method under test: {@link AccountServiceImpl#findDogamMore(AccountDTO)}
     */
    @Test
    void testFindDogamMore() {
        when(accountRepository.selectDogamList((Account) any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        assertTrue(accountServiceImpl.findDogamMore(new AccountDTO()).isEmpty());
        verify(accountRepository).selectDogamList((Account) any(), anyInt(), anyInt());
    }

    /**
     * Method under test: {@link AccountServiceImpl#findDogamMore(AccountDTO)}
     */
    @Test
    void testFindDogamMore2() {
        when(accountRepository.selectDogamList((Account) any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        assertTrue(accountServiceImpl.findDogamMore(null).isEmpty());
        verify(accountRepository).selectDogamList((Account) any(), anyInt(), anyInt());
    }

    /**
     * Method under test: {@link AccountServiceImpl#modifyAccountProfileImg(AccountDTO, String)}
     */
    @Test
    void testModifyAccountProfileImg() {
        when(accountRepository.updateProfileImg((Account) any(), (String) any())).thenReturn(true);
        assertTrue(accountServiceImpl.modifyAccountProfileImg(new AccountDTO(), "https://example.org/example"));
        verify(accountRepository).updateProfileImg((Account) any(), (String) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#modifyAccountProfileImg(AccountDTO, String)}
     */
    @Test
    void testModifyAccountProfileImg2() {
        when(accountRepository.updateProfileImg((Account) any(), (String) any())).thenReturn(false);
        assertFalse(accountServiceImpl.modifyAccountProfileImg(new AccountDTO(), "https://example.org/example"));
        verify(accountRepository).updateProfileImg((Account) any(), (String) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#modifyAccountProfileImg(AccountDTO, String)}
     */
    @Test
    void testModifyAccountProfileImg3() {
        when(accountRepository.updateProfileImg((Account) any(), (String) any())).thenReturn(true);
        assertTrue(accountServiceImpl.modifyAccountProfileImg(null, "https://example.org/example"));
        verify(accountRepository).updateProfileImg((Account) any(), (String) any());
    }

    /**
     * Method under test: {@link AccountServiceImpl#findTermsOfAgreement()}
     */
    @Test
    void testFindTermsOfAgreement() {
        assertNotNull(accountServiceImpl.findTermsOfAgreement());
    }
}

