package com.wpkk.stepicJavaServer01.servlets;

import com.wpkk.stepicJavaServer01.templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


public class Servlet extends HttpServlet{
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        FileWriter fw = new FileWriter("Requests.txt",true);

        fw.write(Instant.now().toString() + " " + request.getRequestURI() + "?" + request.getQueryString() + "\n");
        fw.flush();

        boolean isParameterFound = false;
        String key = "keyNotFound";
        String[] parameters = request.getQueryString().split("&");
        //System.out.println(Arrays.toString(parameters));

        for (String s : parameters) {
            if (s.split("=")[0].equals("key")) {
                key = s.split("=")[1];
                isParameterFound = true;
                break;
            }
        }
        if (!isParameterFound)
            System.out.println("Didn't receive 'key' parameter");

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("key",key);

        response.getWriter().println(PageGenerator.instance().getPage("key.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);

        String message = request.getParameter("message");

        response.setContentType("text/html;charset=utf-8");

        if (message == null || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        pageVariables.put("message", message == null ? "" : message);

        response.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("pathInfo", request.getPathInfo());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());
        return pageVariables;
    }

}
