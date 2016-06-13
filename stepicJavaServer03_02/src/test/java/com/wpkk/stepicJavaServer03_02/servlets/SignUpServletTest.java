package com.wpkk.stepicJavaServer03_02.servlets;

import com.wpkk.stepicJavaServer03_02.accounts.AccountService;
import com.wpkk.stepicJavaServer03_02.accounts.UserProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Matchers;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignUpServletTest {
    private AccountService accountService = mock(AccountService.class);
    private String login = "login1";
    private String password = "password1";
    @Captor
    ArgumentCaptor<UserProfile> argument;

    HttpServletRequest getMockedRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);
        when(request.getPathInfo()).thenReturn("/signup");
        return request;
    }

    HttpServletResponse getMockedResponse(StringWriter writer) throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter printWriter = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(printWriter);
        return response;
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDoGet() throws Exception {
    }

    @Test
    public void testForNullInputSignUp() throws Exception {
        HttpServletRequest request = getMockedRequest();
        StringWriter writer = new StringWriter();
        HttpServletResponse response = getMockedResponse(writer);
        when(request.getParameter("login")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);

        SignUpServlet signUp = new SignUpServlet(accountService);
        signUp.doPost(request, response);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


    @Test
    public void testDuplicateLoginSignUp() throws Exception {
        HttpServletRequest request = getMockedRequest();
        StringWriter writer = new StringWriter();
        HttpServletResponse response = getMockedResponse(writer);

        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        when(accountService.getUserByLogin(anyString())).thenReturn(mock(UserProfile.class));

        SignUpServlet signUp = new SignUpServlet(accountService);
        signUp.doPost(request, response);
        assertEquals("This login is already used by another user", writer.toString().trim());
    }

    @Test
    public void testSuccessfulSignUp() throws Exception {
        HttpServletRequest request = getMockedRequest();
        StringWriter writer = new StringWriter();
        HttpServletResponse response = getMockedResponse(writer);

        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);

        SignUpServlet signUp = new SignUpServlet(accountService);
        signUp.doPost(request, response);
        
        verify(accountService, times(1)).addNewUser(Matchers.refEq(new UserProfile(login, password, "email")));
//        verify(accountService, times(1)).addNewUser(argument.capture());
//        assertEquals(new UserProfile(login, password, "email"), argument.getValue());
    }

}