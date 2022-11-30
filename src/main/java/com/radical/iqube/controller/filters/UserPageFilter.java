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

        System.out.println("------usr page filter");
        System.out.println(req.getRemoteAddr());
        try {
            System.out.println(rqst.getSession(false) + "   from userpage filter");
            rspns.addHeader("Access-Control-Allow-Origin","*");

//            if (rqst.getSession(false) == null) {
//                System.out.println("your session is NULL, redirect to /home");
//
//                rspns.sendRedirect("/home");
//            }else
            if(rqst.getCookies() != null){
                System.out.println("redir to u page");
                rqst.getRequestDispatcher("/userPage").forward(req,resp);
            }else {rspns.sendRedirect("/home");}
        }catch (IOException | ServletException e){e.printStackTrace();}
    }

    @Override
    public void destroy() {}
}
