package com.radical.iqube.controller.servlets;

import com.radical.iqube.model.dao.UserDaoService;
import com.radical.iqube.model.entity.UserEntity;
import com.radical.iqube.optional.Listener;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthServlet extends HttpServlet {
    private UserDaoService service = null;

    @Override
    public void init(ServletConfig config) {service = new UserDaoService();}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        JSONObject credentials = (JSONObject) req.getAttribute("credentials");
        String login = credentials.getString("login");
        String pwd = credentials.getString("password");
        UserEntity user = service.getByLogin(login);

        try {
            if (user != null) {
                if (user.getPassword().equals(pwd)) {
                    req.getSession(true);
                    resp.sendRedirect(req.getServletContext().getContextPath()+ "/userPage");
                }
                else {resp.getWriter().write("incorrect password");}
            } else {resp.getWriter().write("user is not exist");}
        } catch (Exception e) {/*-----log}*/}
    }
}
