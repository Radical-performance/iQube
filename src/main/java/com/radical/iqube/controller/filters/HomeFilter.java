package com.radical.iqube.controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain ch) throws IOException{
        HttpServletRequest rqst = (HttpServletRequest) req;
        HttpServletResponse resp = (HttpServletResponse) res;
        HttpSession ses =rqst.getSession(false);
        System.out.println( "  home filter");
        try {
            if (ses != null || rqst.getCookies() != null) {
                resp.setStatus(303);
                resp.sendRedirect(req.getServletContext().getContextPath()+ "/userPage");
            } else {
//                resp.setStatus(200);
                req.getRequestDispatcher("/home").forward(rqst, resp);
            }
        } catch (ServletException e) {/*logger---->*/}
    }
}
