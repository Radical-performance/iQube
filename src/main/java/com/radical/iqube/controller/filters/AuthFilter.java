package com.radical.iqube.controller.filters;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

public class AuthFilter implements Filter {
    private Logger logger;
    @Override
    public void init(FilterConfig filterConfig){}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) {
        JSONObject credentials = new JSONObject(req.getParameter("credentials"));
        try {
            if (!credentials.getString("login").isEmpty()
                    && !credentials.getString("password").isEmpty()) {
                filterChain.doFilter(req, resp);
            }
        }catch (ServletException | IOException e){
            logger = Logger.getLogger(AuthFilter.class);
            logger.warn(new Date() + "   Exception occured during AuthFilter work: "+ e.getMessage());
            logger = null;
        }
    }
}
