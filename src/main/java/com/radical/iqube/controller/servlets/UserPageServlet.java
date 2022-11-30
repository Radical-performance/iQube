package com.radical.iqube.controller.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

public class UserPageServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession ses =req.getSession(false);
        System.out.println(ses+ "   ses from userpage serv");
        System.out.println("------ usr page servlet");
        System.out.println(req.getRemoteAddr());
        System.out.println(req.getRequestURI());
        System.out.println(req.getHeader("mobile"));
        System.out.println(req.getRequestURI());
        //todo: edit url with appending user nickname to him
        resp.setStatus(200);

        System.out.println(req.getHeader("Referer"));
        if(req.getHeader("User-Agent").contains(" Mobile")){
            req.getRequestDispatcher("/userPage.html").forward(req,resp);
        }else{req.getRequestDispatcher("/userPage.html").forward(req, resp);}
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)  {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() throws ServletException {
    }
}
