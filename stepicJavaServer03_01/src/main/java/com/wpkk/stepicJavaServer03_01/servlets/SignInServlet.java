package com.wpkk.stepicJavaServer03_01.servlets;

import com.wpkk.stepicJavaServer03_01.accounts.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by default on 04.01.2016.
 */
public class SignInServlet extends HttpServlet{
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
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

        if ((accountService.getUserByLogin(login) == null)  || !accountService.getUserByLogin(login).getPass().equals(password)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Authorized");
        }

    }

}
