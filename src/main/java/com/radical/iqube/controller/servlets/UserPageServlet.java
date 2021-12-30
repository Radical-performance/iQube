package com.radical.iqube.controller.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        //todo: edit url with appending user nikname to him
        try {
            resp.setStatus(200);
            req.getRequestDispatcher("/userPage.html").forward(req, resp);
        }catch (ServletException | IOException e){/*log--->*/}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() throws ServletException {
    }
}
