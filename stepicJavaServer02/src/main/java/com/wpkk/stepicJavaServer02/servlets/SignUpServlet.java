package com.wpkk.stepicJavaServer02.servlets;

import com.wpkk.stepicJavaServer02.accounts.AccountService;
import com.wpkk.stepicJavaServer02.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    public final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=utf-8");

        if ((login == null) || (password == null)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (accountService.getUserByLogin(login) != null){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("This login is already used by another user");
        } else {
            accountService.addNewUser(new UserProfile(login,password,"email"));
        }

    }

}
