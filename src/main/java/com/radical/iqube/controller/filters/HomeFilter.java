package com.radical.iqube.controller.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//web-xml description will be replaced with annotations on next steps
public class HomeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain ch) throws ServletException, IOException {
        ch.doFilter(req,res);
    }


}
