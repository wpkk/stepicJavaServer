package com.wpkk.stepicJavaServer06.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.wpkk.stepicJavaServer06.ResourceService.ResourceServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceServlet extends HttpServlet {

    static final Logger logger = LogManager.getLogger(ResourceServlet.class.getName());
    public static final String PAGE_URL = "/resources";
    private ResourceServer resourceServer;

    public ResourceServlet(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("Unsupported method");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        String pathToResource = request.getParameter("path");

        if (pathToResource != null) {
            logger.info("Path = {}", pathToResource);
            response.setStatus(200);
            List lines = Files.readAllLines(Paths.get(pathToResource));
            resourceServer.setResourceFromXML(pathToResource);
            logger.info(Arrays.toString(lines.toArray()));

        } else {
            response.setStatus(403);
            response.getWriter().write("Path parameter required");

        }



    }
}
