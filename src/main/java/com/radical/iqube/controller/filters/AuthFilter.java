package com.radical.iqube.controller.filters;

import com.radical.iqube.optional.Listener;
import org.apache.log4j.Logger;
import org.json.CookieList;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

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

        StringBuilder sb = new StringBuilder();
        String line;
        JSONObject user = null;
        if (session == null || request.getCookies() == null && request.getRequestURI().endsWith("auth")) {

            try {
                BufferedReader reader = req.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                user = new JSONObject(sb.toString());
                System.out.println(user);

                if (!user.getString("login").isEmpty() && !user.getString("password").isEmpty()) {
                    session = request.getSession(true);
                        session.setAttribute("credentials", user);
                        request.getRequestDispatcher("/auth").forward(request, response);
                    } else {
                        request.getRequestDispatcher("/home").forward(request, response);
                    }
                
            } catch (Exception e) {
                //log

                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        } else {
            response.setStatus(303);
            response.sendRedirect(req.getServletContext().getContextPath()+"/iqube/userPage");
        }
    }
}
