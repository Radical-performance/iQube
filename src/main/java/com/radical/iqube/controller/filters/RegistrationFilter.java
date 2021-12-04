package com.radical.iqube.controller.filters;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

public class RegistrationFilter implements Filter {
    private Logger logger;

    @Override
    public void init(FilterConfig filterConfig){}

    @Override
    public void destroy(){}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) {
        JSONObject userJson = new JSONObject(req.getParameter("userData"));
        try {
            if (!userJson.getString("login").isEmpty()
                    && !userJson.getString("password").isEmpty()
                    && !userJson.getString("email").isEmpty()
                    && !userJson.getString("nickname").isEmpty()) {
                filterChain.doFilter(req, resp);
            }
        } catch (ServletException | IOException e) {
            logger = Logger.getLogger(RegistrationFilter.class);
            logger.warn(new Date() + "  Exception occured during RegistrationFilter work: " + e.getMessage());
            logger = null;
        }
    }
}

