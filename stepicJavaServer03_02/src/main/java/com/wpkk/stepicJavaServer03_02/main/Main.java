package com.wpkk.stepicJavaServer03_02.main;

import com.wpkk.stepicJavaServer03_02.accounts.AccountService;
import com.wpkk.stepicJavaServer03_02.dbService.DBService;
import com.wpkk.stepicJavaServer03_02.dbService.DBServiceImpl;
import com.wpkk.stepicJavaServer03_02.dbService.dataSets.UsersDataSet;
import com.wpkk.stepicJavaServer03_02.servlets.SignInServlet;
import com.wpkk.stepicJavaServer03_02.servlets.SignUpServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.Arrays;
import java.util.List;

/**
 * @author v.chibrikov
 *         <p>
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        DBService dbService = new DBServiceImpl();
        List<UsersDataSet> list = dbService.getAllUsers();
        System.out.printf(Arrays.toString(list.toArray()));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
