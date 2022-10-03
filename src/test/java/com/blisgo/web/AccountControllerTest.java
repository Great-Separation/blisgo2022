package com.blisgo.web;

import com.blisgo.domain.entity.Account;
import com.blisgo.domain.repository.AccountRepository;
import com.blisgo.service.AccountService;
import com.blisgo.service.impl.AccountServiceImpl;
import com.blisgo.web.dto.AccountDTO;
import org.apache.catalina.session.PersistentManager;
import org.apache.catalina.session.StandardSession;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AccountController.class})
@ExtendWith(SpringExtension.class)
class AccountControllerTest {
    @Autowired
    private AccountController accountController;

    @MockBean
    private AccountService accountService;

    /**
     * Method under test: {@link AccountController#dictionaryLoadMore(HttpSession, AccountDTO)}
     */
    @Test
    void testDictionaryLoadMore() throws Exception {
        when(accountService.findDogamMore((AccountDTO) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/dogam/more");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link AccountController#login()}
     */
    @Test
    void testLogin() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   javax.servlet.ServletException: Circular view path [/account/login]: would dispatch back to the current handler URL [/account/login] again. Check your ViewResolver setup! (Hint: This may be the result of an unspecified view, due to default view name generation.)
        //       at org.springframework.web.servlet.view.InternalResourceView.prepareForRendering(InternalResourceView.java:210)
        //       at org.springframework.web.servlet.view.InternalResourceView.renderMergedOutputModel(InternalResourceView.java:148)
        //       at org.springframework.web.servlet.view.AbstractView.render(AbstractView.java:316)
        //       at org.springframework.web.servlet.DispatcherServlet.render(DispatcherServlet.java:1405)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.render(TestDispatcherServlet.java:137)
        //       at org.springframework.web.servlet.DispatcherServlet.processDispatchResult(DispatcherServlet.java:1149)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:964)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:201)
        //   In order to prevent login()
        //   from throwing ServletException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   login().
        //   See https://diff.blue/R013 to resolve this issue.

        AccountController accountController = new AccountController(
                new AccountServiceImpl(mock(AccountRepository.class)));
        assertTrue(accountController.login().isReference());
        assertEquals("/account/login", accountController.url);
    }

    /**
     * Method under test: {@link AccountController#register()}
     */
    @Test
    void testRegister() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   javax.servlet.ServletException: Circular view path [/account/register]: would dispatch back to the current handler URL [/account/register] again. Check your ViewResolver setup! (Hint: This may be the result of an unspecified view, due to default view name generation.)
        //       at org.springframework.web.servlet.view.InternalResourceView.prepareForRendering(InternalResourceView.java:210)
        //       at org.springframework.web.servlet.view.InternalResourceView.renderMergedOutputModel(InternalResourceView.java:148)
        //       at org.springframework.web.servlet.view.AbstractView.render(AbstractView.java:316)
        //       at org.springframework.web.servlet.DispatcherServlet.render(DispatcherServlet.java:1405)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.render(TestDispatcherServlet.java:137)
        //       at org.springframework.web.servlet.DispatcherServlet.processDispatchResult(DispatcherServlet.java:1149)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:964)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:201)
        //   In order to prevent register()
        //   from throwing ServletException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   register().
        //   See https://diff.blue/R013 to resolve this issue.

        AccountController accountController = new AccountController(
                new AccountServiceImpl(mock(AccountRepository.class)));
        ModelAndView actualRegisterResult = accountController.register();
        assertTrue(actualRegisterResult.isReference());
        assertSame(actualRegisterResult.getModel(), actualRegisterResult.getModelMap());
        assertEquals("/account/register", accountController.url);
    }

    /**
     * Method under test: {@link AccountController#register()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegister2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   javax.servlet.ServletException: Circular view path [/account/register]: would dispatch back to the current handler URL [/account/register] again. Check your ViewResolver setup! (Hint: This may be the result of an unspecified view, due to default view name generation.)
        //       at org.springframework.web.servlet.view.InternalResourceView.prepareForRendering(InternalResourceView.java:210)
        //       at org.springframework.web.servlet.view.InternalResourceView.renderMergedOutputModel(InternalResourceView.java:148)
        //       at org.springframework.web.servlet.view.AbstractView.render(AbstractView.java:316)
        //       at org.springframework.web.servlet.DispatcherServlet.render(DispatcherServlet.java:1405)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.render(TestDispatcherServlet.java:137)
        //       at org.springframework.web.servlet.DispatcherServlet.processDispatchResult(DispatcherServlet.java:1149)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:964)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:201)
        //   In order to prevent register()
        //   from throwing ServletException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   register().
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.service.AccountService.findTermsOfAgreement()" because "this.accountService" is null
        //       at com.blisgo.web.AccountController.register(AccountController.java:77)
        //   In order to prevent register()
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   register().
        //   See https://diff.blue/R013 to resolve this issue.

        (new AccountController(null)).register();
    }

    /**
     * Method under test: {@link AccountController#mypage(HttpSession, AccountDTO)}
     */
    @Test
    void testMypage() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   javax.servlet.ServletException: Circular view path [/account/mypage]: would dispatch back to the current handler URL [/account/mypage] again. Check your ViewResolver setup! (Hint: This may be the result of an unspecified view, due to default view name generation.)
        //       at org.springframework.web.servlet.view.InternalResourceView.prepareForRendering(InternalResourceView.java:210)
        //       at org.springframework.web.servlet.view.InternalResourceView.renderMergedOutputModel(InternalResourceView.java:148)
        //       at org.springframework.web.servlet.view.AbstractView.render(AbstractView.java:316)
        //       at org.springframework.web.servlet.DispatcherServlet.render(DispatcherServlet.java:1405)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.render(TestDispatcherServlet.java:137)
        //       at org.springframework.web.servlet.DispatcherServlet.processDispatchResult(DispatcherServlet.java:1149)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:964)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:201)
        //   In order to prevent mypage(HttpSession, AccountDTO)
        //   from throwing ServletException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypage(HttpSession, AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.selectDogamList((Account) any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        AccountController accountController = new AccountController(new AccountServiceImpl(accountRepository));
        MockHttpSession session = new MockHttpSession();
        ModelAndView actualMypageResult = accountController.mypage(session, new AccountDTO());
        assertTrue(actualMypageResult.isReference());
        assertSame(actualMypageResult.getModel(), actualMypageResult.getModelMap());
        verify(accountRepository).selectDogamList((Account) any(), anyInt(), anyInt());
        assertEquals("/account/mypage", accountController.url);
    }

    /**
     * Method under test: {@link AccountController#mypage(HttpSession, AccountDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMypage2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   javax.servlet.ServletException: Circular view path [/account/mypage]: would dispatch back to the current handler URL [/account/mypage] again. Check your ViewResolver setup! (Hint: This may be the result of an unspecified view, due to default view name generation.)
        //       at org.springframework.web.servlet.view.InternalResourceView.prepareForRendering(InternalResourceView.java:210)
        //       at org.springframework.web.servlet.view.InternalResourceView.renderMergedOutputModel(InternalResourceView.java:148)
        //       at org.springframework.web.servlet.view.AbstractView.render(AbstractView.java:316)
        //       at org.springframework.web.servlet.DispatcherServlet.render(DispatcherServlet.java:1405)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.render(TestDispatcherServlet.java:137)
        //       at org.springframework.web.servlet.DispatcherServlet.processDispatchResult(DispatcherServlet.java:1149)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:964)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:201)
        //   In order to prevent mypage(HttpSession, AccountDTO)
        //   from throwing ServletException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypage(HttpSession, AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.service.AccountService.findDogam(com.blisgo.web.dto.AccountDTO)" because "this.accountService" is null
        //       at com.blisgo.web.AccountController.mypage(AccountController.java:198)
        //   In order to prevent mypage(HttpSession, AccountDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypage(HttpSession, AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountController accountController = new AccountController(null);
        MockHttpSession session = new MockHttpSession();
        accountController.mypage(session, new AccountDTO());
    }

    /**
     * Method under test: {@link AccountController#mypage(HttpSession, AccountDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMypage3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   javax.servlet.ServletException: Circular view path [/account/mypage]: would dispatch back to the current handler URL [/account/mypage] again. Check your ViewResolver setup! (Hint: This may be the result of an unspecified view, due to default view name generation.)
        //       at org.springframework.web.servlet.view.InternalResourceView.prepareForRendering(InternalResourceView.java:210)
        //       at org.springframework.web.servlet.view.InternalResourceView.renderMergedOutputModel(InternalResourceView.java:148)
        //       at org.springframework.web.servlet.view.AbstractView.render(AbstractView.java:316)
        //       at org.springframework.web.servlet.DispatcherServlet.render(DispatcherServlet.java:1405)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.render(TestDispatcherServlet.java:137)
        //       at org.springframework.web.servlet.DispatcherServlet.processDispatchResult(DispatcherServlet.java:1149)
        //       at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1088)
        //       at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:964)
        //       at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)
        //       at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
        //       at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)
        //       at org.springframework.test.web.servlet.TestDispatcherServlet.service(TestDispatcherServlet.java:72)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //       at org.springframework.mock.web.MockFilterChain$ServletFilterProxy.doFilter(MockFilterChain.java:167)
        //       at org.springframework.mock.web.MockFilterChain.doFilter(MockFilterChain.java:134)
        //       at org.springframework.test.web.servlet.MockMvc.perform(MockMvc.java:201)
        //   In order to prevent mypage(HttpSession, AccountDTO)
        //   from throwing ServletException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypage(HttpSession, AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.servlet.http.HttpSession.getAttribute(String)" because "session" is null
        //       at com.blisgo.web.AccountController.mypage(AccountController.java:197)
        //   In order to prevent mypage(HttpSession, AccountDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypage(HttpSession, AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.selectDogamList((Account) any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        AccountController accountController = new AccountController(new AccountServiceImpl(accountRepository));
        accountController.mypage(null, new AccountDTO());
    }

    /**
     * Method under test: {@link AccountController#mypageUpdateProfileImg(HttpSession, MultipartFile, AccountDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMypageUpdateProfileImg() throws IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at java.util.Objects.requireNonNull(Objects.java:208)
        //       at com.blisgo.util.CloudinaryUtil.uploadImage(CloudinaryUtil.java:50)
        //       at com.blisgo.web.AccountController.mypageUpdateProfileImg(AccountController.java:219)
        //   In order to prevent mypageUpdateProfileImg(HttpSession, MultipartFile, AccountDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdateProfileImg(HttpSession, MultipartFile, AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountController accountController = new AccountController(
                new AccountServiceImpl(mock(AccountRepository.class)));
        MockHttpSession session = new MockHttpSession();
        MockMultipartFile profile_img = new MockMultipartFile("Name",
                new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")));

        accountController.mypageUpdateProfileImg(session, profile_img, new AccountDTO());
    }

    /**
     * Method under test: {@link AccountController#mypageUpdateProfileImg(HttpSession, MultipartFile, AccountDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMypageUpdateProfileImg2() throws IOException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.servlet.http.HttpSession.getAttribute(String)" because "session" is null
        //       at com.blisgo.web.AccountController.mypageUpdateProfileImg(AccountController.java:217)
        //   In order to prevent mypageUpdateProfileImg(HttpSession, MultipartFile, AccountDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdateProfileImg(HttpSession, MultipartFile, AccountDTO).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountController accountController = new AccountController(
                new AccountServiceImpl(mock(AccountRepository.class)));
        MockMultipartFile profile_img = new MockMultipartFile("Name",
                new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")));

        accountController.mypageUpdateProfileImg(null, profile_img, new AccountDTO());
    }

    /**
     * Method under test: {@link AccountController#mypageUpdatePassword(HttpSession, AccountDTO, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMypageUpdatePassword() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        Account account = new Account();
        account.preUpdate();
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.selectAccount((Account) any())).thenReturn(account);
        AccountController accountController = new AccountController(new AccountServiceImpl(accountRepository));
        MockHttpSession session = new MockHttpSession();
        accountController.mypageUpdatePassword(session, new AccountDTO(), "New Pass");
    }

    /**
     * Method under test: {@link AccountController#mypageUpdatePassword(HttpSession, AccountDTO, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMypageUpdatePassword2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.service.AccountService.findAccount(com.blisgo.web.dto.AccountDTO)" because "this.accountService" is null
        //       at com.blisgo.web.AccountController.mypageUpdatePassword(AccountController.java:268)
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountController accountController = new AccountController(null);
        MockHttpSession session = new MockHttpSession();
        accountController.mypageUpdatePassword(session, new AccountDTO(), "New Pass");
    }

    /**
     * Method under test: {@link AccountController#mypageUpdatePassword(HttpSession, AccountDTO, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMypageUpdatePassword3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.web.dto.AccountDTO.getPass()" because the return value of "com.blisgo.service.AccountService.findAccount(com.blisgo.web.dto.AccountDTO)" is null
        //       at com.blisgo.web.AccountController.mypageUpdatePassword(AccountController.java:268)
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountService accountService = mock(AccountService.class);
        when(accountService.findAccount((AccountDTO) any())).thenReturn(null);
        AccountController accountController = new AccountController(accountService);
        MockHttpSession session = new MockHttpSession();
        accountController.mypageUpdatePassword(session, new AccountDTO(), "New Pass");
    }

    /**
     * Method under test: {@link AccountController#mypageUpdatePassword(HttpSession, AccountDTO, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMypageUpdatePassword4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: getAttribute: Session already invalidated
        //       at org.apache.catalina.session.StandardSession.getAttribute(StandardSession.java:1148)
        //       at com.blisgo.web.AccountController.mypageUpdatePassword(AccountController.java:266)
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing IllegalStateException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountService accountService = mock(AccountService.class);
        when(accountService.findAccount((AccountDTO) any())).thenReturn(new AccountDTO());
        AccountController accountController = new AccountController(accountService);
        StandardSession session = new StandardSession(new PersistentManager());
        accountController.mypageUpdatePassword(session, new AccountDTO(), "New Pass");
    }

    /**
     * Method under test: {@link AccountController#mypageUpdatePassword(HttpSession, AccountDTO, String)}
     */
    @Test
    void testMypageUpdatePassword5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountService accountService = mock(AccountService.class);
        when(accountService.findAccount((AccountDTO) any())).thenReturn(new AccountDTO());
        AccountController accountController = new AccountController(accountService);
        MockHttpSession session = new MockHttpSession();
        assertTrue(
                accountController
                        .mypageUpdatePassword(session,
                                new AccountDTO(1, "Nickname", "jane.doe@example.org", "Pass", 1, "Profile Image",
                                        LocalDateTime.of(1, 1, 1, 1, 1)),
                                "New Pass")
                        .isReference());
        verify(accountService).findAccount((AccountDTO) any());
        assertEquals("/account/mypage", accountController.url);
    }

    /**
     * Method under test: {@link AccountController#mypageUpdatePassword(HttpSession, AccountDTO, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMypageUpdatePassword6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blisgo.web.dto.AccountDTO.getPass()" because "accountDTO" is null
        //       at com.blisgo.web.AccountController.mypageUpdatePassword(AccountController.java:268)
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountService accountService = mock(AccountService.class);
        when(accountService.findAccount((AccountDTO) any())).thenReturn(new AccountDTO());
        AccountController accountController = new AccountController(accountService);
        accountController.mypageUpdatePassword(new MockHttpSession(), null, "New Pass");
    }

    /**
     * Method under test: {@link AccountController#mypageUpdatePassword(HttpSession, AccountDTO, String)}
     */
    @Test
    void testMypageUpdatePassword7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   In order to prevent mypageUpdatePassword(HttpSession, AccountDTO, String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   mypageUpdatePassword(HttpSession, AccountDTO, String).
        //   See https://diff.blue/R013 to resolve this issue.

        AccountDTO accountDTO = mock(AccountDTO.class);
        when(accountDTO.getPass()).thenReturn("Pass");
        AccountService accountService = mock(AccountService.class);
        when(accountService.modifyAccountPass((AccountDTO) any(), (String) any())).thenReturn(true);
        when(accountService.findAccount((AccountDTO) any())).thenReturn(accountDTO);
        AccountController accountController = new AccountController(accountService);
        MockHttpSession mockHttpSession = new MockHttpSession();
        ModelAndView actualMypageUpdatePasswordResult = accountController.mypageUpdatePassword(mockHttpSession,
                new AccountDTO(1, "Nickname", "jane.doe@example.org", "Pass", 1, "Profile Image",
                        LocalDateTime.of(1, 1, 1, 1, 1)),
                "New Pass");
        assertFalse(actualMypageUpdatePasswordResult.isReference());
        View view = actualMypageUpdatePasswordResult.getView();
        assertFalse(((RedirectView) view).isPropagateQueryProperties());
        assertFalse(((RedirectView) view).isExposePathVariables());
        assertEquals("/account/login", ((RedirectView) view).getUrl());
        assertEquals("text/html;charset=ISO-8859-1", view.getContentType());
        assertTrue(((RedirectView) view).getAttributesMap().isEmpty());
        verify(accountService).modifyAccountPass((AccountDTO) any(), (String) any());
        verify(accountService).findAccount((AccountDTO) any());
        verify(accountDTO).getPass();
        assertTrue(mockHttpSession.isInvalid());
        assertEquals("/account/login", accountController.url);
    }

    /**
     * Method under test: {@link AccountController#dictionaryLoadMore(HttpSession, AccountDTO)}
     */
    @Test
    void testDictionaryLoadMore2() throws Exception {
        when(accountService.findDogamMore((AccountDTO) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/account/dogam/more");
        postResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link AccountController#login(AccountDTO, HttpSession)}
     */
    @Test
    void testLogin2() throws Exception {
        when(accountService.findAccount((AccountDTO) any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/account/login");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDTO"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/account/register"));
    }

    /**
     * Method under test: {@link AccountController#logout(HttpSession)}
     */
    @Test
    void testLogout() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/account/logout");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    /**
     * Method under test: {@link AccountController#logout(HttpSession)}
     */
    @Test
    void testLogout2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/account/logout");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    /**
     * Method under test: {@link AccountController#mypageDeleteAccount(HttpSession)}
     */
    @Test
    void testMypageDeleteAccount() throws Exception {
        when(accountService.removeAccount((AccountDTO) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/account/mypage");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dogamList"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    /**
     * Method under test: {@link AccountController#mypageUpdateNickname(HttpSession, AccountDTO)}
     */
    @Test
    void testMypageUpdateNickname() throws Exception {
        when(accountService.findAccount((AccountDTO) any())).thenReturn(new AccountDTO());
        when(accountService.modifyAccountNickname((AccountDTO) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/account/mypage/update-nickname");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDTO", "dogamList"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/account/mypage"));
    }

    /**
     * Method under test: {@link AccountController#mypageUpdateNickname(HttpSession, AccountDTO)}
     */
    @Test
    void testMypageUpdateNickname2() throws Exception {
        when(accountService.findAccount((AccountDTO) any())).thenReturn(new AccountDTO());
        when(accountService.modifyAccountNickname((AccountDTO) any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/account/mypage/update-nickname");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("accountDTO", "dogamList"))
                .andExpect(MockMvcResultMatchers.view().name("/account/chgpw"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/account/chgpw"));
    }

    /**
     * Method under test: {@link AccountController#registerPOST(AccountDTO)}
     */
    @Test
    void testRegisterPOST() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/account/register");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("accountDTO", String.valueOf(new AccountDTO()));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link AccountController#verify()}
     */
    @Test
    void testVerify() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/account/verify");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dogamList", "termsOfAgreement"))
                .andExpect(MockMvcResultMatchers.view().name("/account/register"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/account/register"));
    }

    /**
     * Method under test: {@link AccountController#verify()}
     */
    @Test
    void testVerify2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/account/verify");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dogamList", "termsOfAgreement"))
                .andExpect(MockMvcResultMatchers.view().name("/account/register"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/account/register"));
    }
}

