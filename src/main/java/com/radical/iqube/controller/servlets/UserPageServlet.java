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
        System.out.println(req.getSession(false)+ "   ses from userpage serv");
        //todo: edit url with appending user nikname to him
            req.getRequestDispatcher("/userPage.html").forward(req, resp);
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
