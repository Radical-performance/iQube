package com.radical.iqube.controller.filters;

import com.radical.iqube.optional.Listener;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthFilter implements Filter {
    private Logger logger;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        if (session == null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = req.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    JSONObject user = new JSONObject(sb.toString());
                    if (!user.getString("login").isEmpty() && !user.getString("password").isEmpty()) {
                        request.setAttribute("credentials", user);
                        request.getRequestDispatcher("/auth").forward(request, response);}
                    else {request.getRequestDispatcher("/home").forward(request, response);}
                }
            } catch (Exception e) {System.out.println(e.getMessage());}
        } else {response.sendRedirect("/iqube/userPage");}
    }
}
