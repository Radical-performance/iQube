package com.radical.iqube.controller.servlets;

import com.radical.iqube.model.dao.UserDao;
import com.radical.iqube.model.dao.UserDaoService;
import com.radical.iqube.model.entity.UserEntity;
import com.radical.iqube.model.entity.UserEntityBuilderImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDaoService service = new UserDaoService();
        HttpSession session = req.getSession(false);
        JSONObject object = (JSONObject) session.getAttribute("userData");

        System.out.println("from registration servlet");
        UserEntity newUser = new UserEntityBuilderImpl()
                .setLogin(object.getString("login"))
                .setPassword(object.getString("password"))
                .setEmail(object.getString("email"))
                .setNickName(object.getString("nickname"))
                .build();
//        Cookie usrLogin = new Cookie("userNickname",newUser.getNickname());
//        Cookie usrPwd = new Cookie("userPassword",newUser.getPassword());

        if(service.createUser(newUser)){

            resp.setStatus(200);
//            resp.setHeader("Connection", "Keep-Alive");
            resp.setHeader("Access-Control-Allow-Origin","*");
            resp.setHeader("Connection","Close");
//            resp.addCookie(usrLogin);
//            resp.addCookie(usrPwd);
//            resp.sendRedirect("/userPage");
            req.getSession(false).invalidate();
            resp.sendRedirect("/home");
        }else{
            resp.setStatus(303);

            resp.addHeader("isRegistered", "false");
            resp.sendRedirect("/home");
        }
    }

}
