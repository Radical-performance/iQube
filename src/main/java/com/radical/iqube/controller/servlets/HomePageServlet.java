package com.radical.iqube.controller.servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//web-xml description will be replaced with annotations on next steps
public class HomePageServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(HomePageServlet.class);
    public HomePageServlet() {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/home.html").forward(req,resp);
    }
}
