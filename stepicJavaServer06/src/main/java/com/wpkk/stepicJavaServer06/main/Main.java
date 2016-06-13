package com.wpkk.stepicJavaServer06.main;

import com.wpkk.stepicJavaServer06.ResourceService.ResourceServer;
import com.wpkk.stepicJavaServer06.ResourceService.ResourceServerController;
import com.wpkk.stepicJavaServer06.ResourceService.ResourceServerControllerMBean;
import com.wpkk.stepicJavaServer06.accountServer.AccountServer;
import com.wpkk.stepicJavaServer06.accountServer.AccountServerController;
import com.wpkk.stepicJavaServer06.accountServer.AccountServerControllerMBean;
import com.wpkk.stepicJavaServer06.accountServer.AccountServerI;
import com.wpkk.stepicJavaServer06.servlets.ResourceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.wpkk.stepicJavaServer06.servlets.HomePageServlet;
import resources.TestResource;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @author a.akbashev
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            logger.error("Use port as the first argument");
//            System.exit(1);
//        }

//        String portString = args[0];
//        int port = Integer.valueOf(portString);

        int port = 8080;

        logger.info("Starting at http://127.0.0.1:{}", port);

        AccountServerI accountServer = new AccountServer(10);
        ResourceServer resourceServer = new ResourceServer(new TestResource());

       //AccountServerControllerMBean serverStatistics = new AccountServerController(accountServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        //ObjectName name = new ObjectName("ServerManager:type=AccountServerController.userLimit");
       // mbs.registerMBean(serverStatistics, name);
        ResourceServerControllerMBean resourceController = new ResourceServerController(resourceServer);
        ObjectName resource = new ObjectName("Admin:type=ResourceServerController");
        mbs.registerMBean(resourceController, resource);

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        //context.addServlet(new ServletHolder(new HomePageServlet(accountServer)), HomePageServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new ResourceServlet(resourceServer)), ResourceServlet.PAGE_URL);


        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        logger.info("Server started");

        server.join();
    }
}
