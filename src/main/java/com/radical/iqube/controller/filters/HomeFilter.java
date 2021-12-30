package com.radical.iqube.controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//web-xml description will be replaced with annotations on next steps
public class HomeFilter implements Filter {
//    private Logger logger;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain ch) {
        HttpServletRequest rqst = (HttpServletRequest) req;
        HttpServletResponse resp = (HttpServletResponse) res;
        try {
            if (rqst.getSession(false) != null || ((HttpServletRequest) req).getCookies() != null) {
                resp.setStatus(303);
                resp.sendRedirect(req.getServletContext().getContextPath()+ "/iqube/userPage");
            } else {
                req.getRequestDispatcher("/home").forward(rqst, resp);
            }
        } catch (IOException | ServletException e) {/*logger---->*/}
    }
}
