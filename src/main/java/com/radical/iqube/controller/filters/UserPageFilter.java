package com.radical.iqube.controller.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserPageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) {
        HttpServletRequest rqst= (HttpServletRequest) req;
        HttpServletResponse rspns = (HttpServletResponse) resp;
        try {
            System.out.println(rqst.getSession(false) + "   from userpage filter");
            if (rqst.getSession(false) == null) {
                System.out.println("your session is NULL, redirect to /home");
                rspns.getWriter().write("not authorized");

                rspns.sendRedirect(req.getServletContext().getContextPath()+"/home");
                System.out.println("redir to u page");
            }else{req.getRequestDispatcher("/userPage").forward(req,resp);}
        }catch (IOException | ServletException e){/*log---->*/}
    }

    @Override
    public void destroy() {}
}
