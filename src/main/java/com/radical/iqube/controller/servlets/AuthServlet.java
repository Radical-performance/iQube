package com.radical.iqube.controller.servlets;

import com.radical.iqube.model.dao.UserDaoService;
import com.radical.iqube.model.entity.UserEntity;
import com.radical.iqube.optional.Listener;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.http.*;

public class AuthServlet extends HttpServlet {
    private UserDaoService service = null;

    @Override
    public void init(ServletConfig config) {
        service = new UserDaoService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        JSONObject credentials = (JSONObject) req.getAttribute("credentials");
        String login = credentials.getString("login");
        String pwd = credentials.getString("password");
        UserEntity user = service.getByLogin(login);

        try {
            if (user != null) {
                if (user.getPassword().equals(pwd)) {

                    //юзать куки не вариант, так ка это может быть причиной CRSF атаки
                    //but i need to have 'dirt' working copy on 31.12..but using of jwt (refreshable) without normal
                    // understanding of that technoligy(+ssl)  not my choise
                    // that's why i'll use cookie to auth below and replace it on jwt later..'i believe :D'
                    Cookie usrLogin = new Cookie("userNickname",user.getNickname());
                    Cookie usrPwd = new Cookie("userPassword",user.getPassword());
                    req.getSession(true);
                    resp.setStatus(303);
                    resp.addHeader("Access-Control-Allow-Origin","origin");
                    resp.addHeader("Connection","Keep-Alive");
                    resp.addHeader("accepted","logged in");
                    resp.addCookie(usrLogin);
                    resp.addCookie(usrPwd);
                    resp.sendRedirect(req.getServletContext().getContextPath() + "/userPage");

                } else {
                    resp.getWriter().write("incorrect password");
                }


            } else {
                resp.getWriter().write("user is not exist");
            }

        } catch (Exception e) {/*-----log}*/}
    }
}
