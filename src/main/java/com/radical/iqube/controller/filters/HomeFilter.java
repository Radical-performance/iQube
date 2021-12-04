package com.radical.iqube.controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

//web-xml description will be replaced with annotations on next steps
public class HomeFilter implements Filter {
    private Logger logger;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain ch) {
        try {
            ch.doFilter(req,res);
        } catch (IOException | ServletException e) {
            logger = Logger.getLogger(HomeFilter.class);
            logger.warn(new Date() + "  Exception occured during HoomeFilter work:  "+ e.getMessage());
        }

    }


}
